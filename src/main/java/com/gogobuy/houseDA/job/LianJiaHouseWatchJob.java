package com.gogobuy.houseDA.job;

import com.gogobuy.houseDA.constants.HouseStateEnum;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.manage.LianJiaAppApi;
import com.gogobuy.houseDA.manage.LianJiaWebApi;
import com.gogobuy.houseDA.notice.INotifier;
import com.gogobuy.houseDA.service.IHouseDataService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
@Slf4j
public class LianJiaHouseWatchJob {

    @Autowired
    private LianJiaWebApi lianJiaWebApi;

    @Autowired
    private LianJiaAppApi lianJiaAppApi;

    @Autowired
    private IHouseDataService houseDataService;

    @Autowired
    private List<INotifier> noticeList;


    @Scheduled(fixedDelay = 100000) //每5秒执行一次
    public void watch() throws IOException {
        process("南海家园");
    }

    public void process(String communityName) throws IOException {
        // 记录本次信息变动的房源信息，用以发送通知
        Map<HouseStateEnum, List<HouseData>> houseDataChangeMap = Maps.newHashMap();


        // 已关注房源状态变动信息处理
        followedHouseDataChangeProcess(houseDataChangeMap);

        // 新增房源监听
        newHouseDataWatchProcess(communityName, houseDataChangeMap);

        notify(houseDataChangeMap);
    }

    private void notify(Map<HouseStateEnum, List<HouseData>> houseDataChangeMap) {
        for (INotifier notice : noticeList) {
            notice.send(houseDataChangeMap);
        }
    }

    private void newHouseDataWatchProcess(String communityName, Map<HouseStateEnum, List<HouseData>> houseDataChangeMap) throws IOException {
        // 查询小区所有房源
        List<String> houseDataList = lianJiaWebApi.communitySellingHouse(communityName);


        Map<String, HouseData> houseDataMap = houseDataService.toMap(houseDataList);

        // 判断是否已经在数据库中
        for (String houseCode : houseDataList) {
            HouseData data = houseDataMap.get(houseCode);

            if (data != null) {
                // 已经在数据库中，不做处理
                continue;
            }

            // 不在数据库中，则关注后，存储到数据库
            int i = lianJiaWebApi.followHouse(houseCode);
            log.info("新增关注房源 houseCode=【{}】关注结果：【{}】", houseCode, i);

            // 查询房源信息

            HouseData newHouseData = lianJiaWebApi.houseDetail(houseCode);
            newHouseData.setHouseState(HouseStateEnum.SELLING.getCode());

            houseDataService.save(newHouseData);

            addChangeList(houseDataChangeMap, HouseStateEnum.SELLING, newHouseData);
        }
    }

    private void addChangeList(Map<HouseStateEnum, List<HouseData>> houseDataChangeMap, HouseStateEnum stateEnum, HouseData data) {
        houseDataChangeMap.putIfAbsent(stateEnum, Lists.newArrayList());
        houseDataChangeMap.computeIfPresent(stateEnum, (k, v) -> {
            v.add(data);
            return v;
        });
    }

    private void followedHouseDataChangeProcess(Map<HouseStateEnum, List<HouseData>> houseDataChangeMap) {
        // 查询已关注房源
        List<HouseData> followedHouseDataList = lianJiaAppApi.queryFollowedHouseData();
        if (CollectionUtils.isEmpty(followedHouseDataList)) {
            log.info("没有已关注房源信息");
            return;
        }


        for (HouseData followedHouseData : followedHouseDataList) {
            String houseCode = followedHouseData.getHouseCode();
            // 查询数据库中的房源信息
            HouseData houseDataInDb = houseDataService.queryHouseDataByHouseCode(houseCode);

            if (houseDataInDb == null){
                houseDataService.save(followedHouseData);
                continue;
            }

            String followedHouseState = followedHouseData.getHouseState();

            String inDbHouseState = houseDataInDb.getHouseState();

            // 若关注的房源是已售状态，而数据库中的房源状态不是已售状态，则更改数据库房源状态为已售
            if (Objects.equals(followedHouseState, HouseStateEnum.SOLD.getCode()) && !Objects.equals(inDbHouseState, HouseStateEnum.SOLD.getCode())) {
                HouseData data = new HouseData();
                data.setHouseCode(houseCode);

                data.setHouseState(HouseStateEnum.SOLD.getCode());

                data.setDealUnitPriceStr(followedHouseData.getDealUnitPriceStr());
                data.setDealPriceStr(followedHouseData.getPriceStr());
                data.setDealPrice(followedHouseData.getPrice());
                data.setDealUnitPrice(followedHouseData.getDealUnitPrice());
                data.setDealTime(followedHouseData.getDealTime());
                houseDataService.updateHouseDataByHouseCode(data);


                // 记录本次信息变动的房源信息，用以发送通知
                addChangeList(houseDataChangeMap, HouseStateEnum.SOLD, data);

                // 取关房源，腾出关注位置
                int i = lianJiaWebApi.unFollowHouse(houseCode);
                log.info("取关房源，houseCode=【{}】取关结果=【{}】", houseCode, i);
            }

            // 若关注的房源是已售，数据库房源也是已售，则直接取关房源即可
            if ((Objects.equals(followedHouseState, HouseStateEnum.SOLD.getCode()) && Objects.equals(inDbHouseState, HouseStateEnum.SOLD.getCode()))
            || Objects.equals(followedHouseState, HouseStateEnum.END_SALE.getCode()) && Objects.equals(inDbHouseState, HouseStateEnum.END_SALE.getCode())) {
                // 取关房源，腾出关注位置
                int i = lianJiaWebApi.unFollowHouse(houseCode);
                log.info("取关房源，houseCode=【{}】取关结果=【{}】", houseCode, i);
            }

            // 若关注的房源是停售，数据库房源为在售，则更改数据库房源状态为停售，并取消关注
            if (Objects.equals(followedHouseState, HouseStateEnum.END_SALE.getCode()) && !Objects.equals(inDbHouseState, HouseStateEnum.END_SALE.getCode())) {
                HouseData data = new HouseData();
                data.setHouseCode(houseCode);

                data.setHouseState(HouseStateEnum.END_SALE.getCode());
                houseDataService.updateHouseDataByHouseCode(data);

                // 记录本次信息变动的房源信息，用以发送通知
                addChangeList(houseDataChangeMap, HouseStateEnum.END_SALE, data);
            }

        }
    }
}
