package com.gogobuy.houseDA.manage;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gogobuy.houseDA.constants.LianJiaProperties;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.utils.HttpClientUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LianJiaWebApi implements LianJiaApi {

    @Autowired
    private LianJiaProperties lianJiaProperties;


    @Override
    public List<String> communitySellingHouse(String communityName) throws IOException {

        int pageNo = 1;

        List<String> resultList = new ArrayList<>();


        while (true) {
            String format = String.format(lianJiaProperties.getCommunityQueryWebApi(), pageNo++, communityName);

            String content = HttpClientUtil.get(format, null);
            Document doc = Jsoup.parse(content);


            Elements sellListContent = doc.getElementsByClass("sellListContent");
            if (sellListContent.isEmpty()) {
                break;
            }
            Element sellContent = sellListContent.get(0);
            // 所有的子
            Elements children = sellContent.children();

            List<String> childHouseCodeList = children.stream()
                    .map(c -> c.attr("data-lj_action_housedel_id"))
                    .collect(Collectors.toList());


            if (CollectionUtil.isEmpty(childHouseCodeList)) {
                break;
            }
            resultList.addAll(childHouseCodeList);
        }
        return resultList;
    }

    /**
     * 包装了WEB端关注房源的逻辑逻辑
     *
     * @param houseCode 链家房源的唯一编码
     * @return -1失败，0成功
     */
    @Override
    public int followHouse(String houseCode) {
        /**
         * 链家接口返回数据结构
         * {
         *     "request_id": "625101347",
         *     "uniq_id": "CF75-ECE3-368D-9BB7-4D7CA4AC88CA",
         *     "errno": 0,
         *     "error": "",
         *     "data": {
         *         "status": 0,
         *         "msg": "操作成功"
         *     }
         * }
         */
        String followHouseFullUrl = String.format(lianJiaProperties.getFollowHouseWebApi(), houseCode);
        log.info("调用链家关注房源接口请求值=【{}】", followHouseFullUrl);

        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Referer", " https://bj.lianjia.com"));
        headers.add(new BasicHeader("cookie", lianJiaProperties.getWebCookie()));

        String strResult = HttpClientUtil.get(followHouseFullUrl, headers);
        log.info("调用链家关注房源接口返回结果strResult=【{}】", strResult);

        JSONObject followResultJsonObject = JSON.parseObject(strResult);

        JSONObject data = followResultJsonObject.getJSONObject("data");

        if (data == null) {
            log.error("调用链家关注房源接口返回结果中data为空");
            return -1;
        }
        Integer status = data.getInteger("status");
        if (!Objects.equals(status, 0)) {
            String msg = data.getString("msg");
            log.error("调用链家关注房源接口返回结果中status不为0，msg=【{}】", msg);
            return -1;
        }
        return 0;
    }


    /**
     * 包装了web取关房源的逻辑
     *
     * @param houseCode
     * @return
     */
    @Override
    public int unFollowHouse(String houseCode) {
        /**
         * 链家接口返回数据结构
         * {
         *     "code": 1,
         *     "data": true,
         *     "msg": "取消关注成功"
         * }
         */


        log.info("调用链家取消关注房源接口api=【{}】houseCode=【{}】", lianJiaProperties.getUnfollowHouseWebApi(), houseCode);

        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Referer", " https://bj.lianjia.com"));
        headers.add(new BasicHeader("cookie", lianJiaProperties.getWebCookie()));

        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", houseCode));
        params.add(new BasicNameValuePair("type", "ershoufang"));

        String strResult = HttpClientUtil.post(lianJiaProperties.getUnfollowHouseWebApi(), headers, params);
        log.info("调用链家取消关注房源接口返回结果strResult=【{}】", strResult);

        JSONObject followResultJsonObject = JSON.parseObject(strResult);

        Integer code = followResultJsonObject.getInteger("code");

        //  code 为1表示成功
        if (!Objects.equals(code, 1)) {
            String msg = followResultJsonObject.getString("msg");
            log.error("调用链家取消关注房源接口返回结果中status不为0，msg=【{}】", msg);
            return -1;
        }
        return 0;
    }


    @Override
    public HouseData houseDetail(String houseCode) {

        String houseDetailWebFullApi = String.format(lianJiaProperties.getHouseDetailWebApi(), houseCode);
        String httpResponse = HttpClientUtil.get(houseDetailWebFullApi, null);

        Document document = Jsoup.parse(httpResponse);


        HouseData data = new HouseData();

        data.setHouseCode(houseCode);

        String title = document.getElementsByClass("main").get(0).text();
        data.setTitle(title);

        String communityName = document.getElementsByClass("communityName").get(0).getElementsByClass("info").get(0).text();
        data.setCommunityName(communityName);

        Elements baseInfo = document.getElementById("introduction").getElementsByClass("introContent").get(0).getElementsByClass("base").get(0).getElementsByTag("li");


        String houseLayout = baseInfo.get(0).text().substring(4);
        data.setHouseLayout(houseLayout);

        String floorState = baseInfo.get(1).text().substring(4);;
        data.setFloorState(floorState);

        String area = baseInfo.get(2).text().substring(4);;
        String areaNum = StringUtils.removeEnd(area, "㎡");
        data.setArea(Double.valueOf(areaNum));

        String elevator = baseInfo.get(9).text().substring(4);;
        data.setElevator(elevator);


        Elements transactionInfo = document.getElementById("introduction").getElementsByClass("introContent").get(0).getElementsByClass("transaction").get(0).getElementsByTag("li");
        data.setGuapaiTime(transactionInfo.get(0).getElementsByTag("span").get(1).text());

        data.setLastTransactionTime(transactionInfo.get(2).getElementsByTag("span").get(1).text());

        data.setHouseAgeAfterLastTransaction(transactionInfo.get(4).getElementsByTag("span").get(1).text());


        try {
            data.setCoreSellPoint(document.getElementsByClass("introContent showbasemore").get(0).getElementsByClass("baseattribute clear").get(0).getElementsByClass("content").get(0).text());
            data.setCommunityDesc(document.getElementsByClass("introContent showbasemore").get(0).getElementsByClass("baseattribute clear").get(1).getElementsByClass("content").get(0).text());
        } catch (Exception e) {
            log.error("解析核心卖点和小区介绍异常", e);
        }


        data.setCommunityName(document.getElementsByClass("communityName").get(0).getElementsByTag("a").get(0).text());

        String unitPriceValue = document.getElementsByClass("price-container").get(0).getElementsByClass("unitPriceValue").get(0).text().replace("元/平米", "");
        data.setUnitPrice(Integer.parseInt(unitPriceValue));

        int totalPrice = Integer.parseInt(document.getElementsByClass("price-container").get(0).getElementsByClass("total").get(0).text());
        data.setPrice(totalPrice);

        data.setPriceStr(totalPrice + "万");

        return data;
    }
}
