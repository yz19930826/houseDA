package com.gogobuy.houseDA.manage;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gogobuy.houseDA.constants.LianJiaProperties;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.utils.HeaderReader;
import com.gogobuy.houseDA.utils.HttpClientUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class LianJiaAppApi implements LianJiaApi {


    @Autowired
    private LianJiaProperties lianJiaProperties;

    @Resource(name = "appFollowQueryApiHeaders")
    private List<Pair<String, List<Header>>> appQueryFollowHouseHeaders;



    @Override
    public List<HouseData> queryFollowedHouseData() {


        List<HouseData> resultList = Lists.newArrayList();

        for (int i = 0; i < appQueryFollowHouseHeaders.size(); i++) {

            String api = lianJiaProperties.getFollowQueryApi().get(i);

            Pair<String, List<Header>> headerPair = appQueryFollowHouseHeaders.get(i);

            String followStr = HttpClientUtil.getNoCheckedException(api, headerPair.getValue(), null);

            // 反序列化
            JSONObject jsonObject = JSONUtil.parseObj(followStr);
            JSONObject data = jsonObject.getJSONObject("data");
            Integer hasMoreData = data.getInt("has_more_data");



            // 解析房源
            JSONArray list = data.getJSONArray("list");

            for (Object o : list) {
                JSONObject houseDataJsonObj = (JSONObject) o;
                HouseData houseData = new HouseData();
                houseData.setArea(houseDataJsonObj.getBigDecimal("area").doubleValue());
                houseData.setCircleName(houseDataJsonObj.getStr("bizcircle_name"));
                houseData.setBedroomNum(houseDataJsonObj.getInt("blueprint_bedroom_num"));
                houseData.setHallNum(houseDataJsonObj.getInt("blueprint_hall_num"));
                houseData.setCityId(houseDataJsonObj.getInt("city_id"));

                JSONArray colorTags = houseDataJsonObj.getJSONArray("color_tags");
                if (colorTags != null) {
                    String tags = colorTags.stream()
                            .map(h -> ((JSONObject) h).getStr("desc"))
                            .collect(Collectors.joining("|"));


                    houseData.setTags(tags);
                }

                houseData.setCommunityId(houseDataJsonObj.getStr("community_id"));
                houseData.setCommunityName(houseDataJsonObj.getStr("community_name"));
                houseData.setHomePic(houseDataJsonObj.getStr("cover_pic"));
                houseData.setFloorState(houseDataJsonObj.getStr("floor_state"));
//                houseData.setHouseFollowedAmount(houseDataJsonObj.getStr("houseDataJsonObj"));
                houseData.setHouseCode(houseDataJsonObj.getStr("house_code"));
                houseData.setHouseState(houseDataJsonObj.getStr("house_state"));
                houseData.setHouseType(houseDataJsonObj.getStr("house_type"));
                houseData.setPrice(houseDataJsonObj.getInt("price"));
                houseData.setUnitPrice(houseDataJsonObj.getInt("unit_price"));


                houseData.setPriceStr(houseDataJsonObj.getStr("price_str"));
                houseData.setUnitPriceStr(houseDataJsonObj.getStr("unit_price_str"));

                houseData.setOrientation(houseDataJsonObj.getStr("orientation"));
                houseData.setTitle(houseDataJsonObj.getStr("title"));
                houseData.setDealTime(houseDataJsonObj.getStr("deal_time"));

                houseData.setAllData(houseDataJsonObj.toString());

                resultList.add(houseData);
            }

            if (hasMoreData != 1){
                break;
            }

        }
        return resultList;
    }

}
