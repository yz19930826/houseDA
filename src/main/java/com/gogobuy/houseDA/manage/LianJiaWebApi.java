package com.gogobuy.houseDA.manage;

import cn.hutool.core.collection.CollectionUtil;
import com.gogobuy.houseDA.constants.LianJiaWebConstant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LianJiaWebApi implements LianJiaApi{

    @Override
    public List<String> communitySellingHouse(String communityName) throws IOException {

        int pageNo = 1;

        List<String> resultList = new ArrayList<>();

        while(true){
            Document doc = Jsoup.connect(String.format(LianJiaWebConstant.COMMUNITY_QUERY_API,pageNo ++ ,communityName)).get();
            Elements sellListContent = doc.getElementsByClass("sellListContent");
            if (sellListContent.isEmpty()){
                break;
            }
            Element sellContent = sellListContent.get(0);
            // 所有的子
            Elements children = sellContent.children();

            List<String> childHouseCodeList = children.stream()
                    .map(c -> c.attr("data-lj_action_housedel_id"))
                    .collect(Collectors.toList());


            if (CollectionUtil.isEmpty(childHouseCodeList)){
                break;
            }
            resultList.addAll(childHouseCodeList);
        }
        return resultList;
    }
}
