package com.gogobuy.houseDA.manage;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gogobuy.houseDA.constants.LianJiaWebConstant;
import com.gogobuy.houseDA.entity.HouseData;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class LianJiaAppApi implements LianJiaApi{

    @Override
    public List<HouseData> queryFollowedHouseData(String cookie){


        List<HouseData> houseDataList = new ArrayList<>();

        int limitOffset = 0;
        while(true){
            long nowSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

            String followStr = HttpRequest.get(String.format(LianJiaWebConstant.FOLLOW_HOUSE_QUERY_API,limitOffset++,nowSeconds))
                    // 尝试下放网页版的cookie
                    .cookie(cookie)//
                    .header("user-agent","HomeLink 9.78.1;iPhone12,1;iOS 15.4;")
                    .header("systeminfo-s","iOS;15.4")
                    .header("parentsceneid","6516473739220752385")
                    .header("channel","lianjia")
                    .header("lng","0")
                    .header("lat","0")
                    .header("page-schema","profile%2Fershoufollowing")
                    .header("dynamic-sdk-version"," 1.1.0")
                    .header("lianjia-version"," 9.78.1")
                    .header("device-id-s","683F42F8-A77D-4BA4-B585-6D809B989373;;")
                    .header("appinfo-s","HomeLink;9.78.1;9.78.1.0")
                    .header("authorization","MjAxNzAzMjRfaW9zOjNlZTRlMjg2ZmYzNTNlMDdhZWM5MGI3ZDc0NzAzNDM2MWVhNzdjZjA=")
                    .header("lianjia-im-version","1")
                    .header("hardware-s","iPhone12,1")
                    .header("accept-language","zh-Hans-CN;q=1")
                    .header("lianjia-recommend-allowable","1")
                    .header("accept"," */*")
                    .header("accept-encoding"," gzip")
                    .header("lianjia-city-id"," 310000")
                    .header("wll-kgsa","LJAPPVI accessKeyId=AFalPXNCeS8yoPy8; nonce=a77GtfVcNJQMl4LGLddW3YI0fFfbi2X5; timestamp=1677247082; signedHeaders=SystemInfo-s,Device-id-s,Channel-s,AppInfo-s,Hardware-s; signature=l9AT0ai9CZ2SFlBAkqwobiWCK253GWEQJXJFP6XXeTs=")
                    .header("channel-s"," lianjia")
                    .header("beikebasedata","%7B%0A%20%20%22duid%22%20%3A%20%22D2%2B1IcEY6yNVnEhSHjkeZypPbeFmpKMv5uXn79RBiTsHYXf6%22%2C%0A%20%20%22appVersion%22%20%3A%20%229.78.1%22%0A%7D")
                    .header("Connection","close")
                    .timeout(20000)//超时，毫秒
                    .execute()
                    .body();

            // 反序列化
            JSONObject jsonObject = JSONUtil.parseObj(followStr);
            JSONObject data = jsonObject.getJSONObject("data");
            Integer hasMoreData = data.getInt("has_more_data");

            // 解析房源
            JSONArray list = data.getJSONArray("list");

            for (Object o : list) {
                JSONObject houseDataJsonObj = (JSONObject)o;
                HouseData houseData = new HouseData();
                houseData.setArea(houseDataJsonObj.getBigDecimal("area"));
                houseData.setCircleName(houseDataJsonObj.getStr("bizcircle_name"));
                houseData.setBedroomNum(houseDataJsonObj.getInt("blueprint_bedroom_num"));
                houseData.setHallNum(houseDataJsonObj.getInt("blueprint_hall_num"));
                houseData.setCityId(houseDataJsonObj.getInt("city_id"));

                JSONArray colorTags = houseDataJsonObj.getJSONArray("color_tags");
                String tags = colorTags.stream()
                        .map(h -> ((JSONObject) h).getStr("desc"))
                        .collect(Collectors.joining("|"));


                houseData.setTags(tags);

                houseData.setCommunityId(houseDataJsonObj.getStr("community_id"));
                houseData.setCommunityName(houseDataJsonObj.getStr("community_name"));
                houseData.setHomePic(houseDataJsonObj.getStr("cover_pic"));
                houseData.setFloorState(houseDataJsonObj.getStr("floor_state"));
                houseData.setHouseFollowedAmount(houseDataJsonObj.getStr("houseDataJsonObj"));
                houseData.setHouseCode(houseDataJsonObj.getStr("house_code"));
                houseData.setHouseState(houseDataJsonObj.getStr("house_state"));
                houseData.setHouseType(houseDataJsonObj.getStr("house_type"));
                houseData.setPrice(houseDataJsonObj.getInt("price"));
                houseData.setUnitPrice(houseDataJsonObj.getInt("unit_price"));
                houseData.setOrientation(houseDataJsonObj.getStr("orientation"));
                houseData.setTitle(houseDataJsonObj.getStr("title"));
                houseData.setDealTime(houseDataJsonObj.getStr("deal_time"));
                houseDataList.add(houseData);
            }


            if (Integer.valueOf(0).equals(hasMoreData)){
                break;
            }

        }
        return houseDataList;
    }

}
