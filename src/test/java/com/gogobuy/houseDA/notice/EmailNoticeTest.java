package com.gogobuy.houseDA.notice;

import com.gogobuy.houseDA.constants.HouseStateEnum;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.service.IHouseDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class EmailNoticeTest {

    @Autowired
    private EmailNotice emailNotice;

    @Autowired
    private IHouseDataService houseDataService;

    @Test
    void send() {
        List<HouseData> allHouseData = houseDataService.queryAll();


        Map<HouseStateEnum,List<HouseData>> map = new HashMap<>();

        for (HouseData houseData : allHouseData) {
            map.putIfAbsent(HouseStateEnum.getByCode(houseData.getHouseState()),new ArrayList<>());
            map.computeIfPresent(HouseStateEnum.getByCode(houseData.getHouseState()), new BiFunction<HouseStateEnum, List<HouseData>, List<HouseData>>() {
                @Override
                public List<HouseData> apply(HouseStateEnum houseStateEnum, List<HouseData> houseDataList) {
                    houseDataList.add(houseData);
                    return houseDataList;
                }
            });
        }

        emailNotice.send(map);
    }
}