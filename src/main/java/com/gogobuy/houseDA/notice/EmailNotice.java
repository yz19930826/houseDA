package com.gogobuy.houseDA.notice;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.mail.MailUtil;
import com.gogobuy.houseDA.constants.HouseStateEnum;
import com.gogobuy.houseDA.entity.HouseData;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class EmailNotice implements INotifier {

    @Override
    public void send(Map<HouseStateEnum, List<HouseData>> houseMapForAllState) {
        if (MapUtil.isEmpty(houseMapForAllState)){
            return;
        }

        String html = buildHtml(houseMapForAllState);

        MailUtil.send("405199299@qq.com","房源信息",html,true);
    }

    private String buildHtml(Map<HouseStateEnum, List<HouseData>> houseMapForAllState) {


        List<List<String>> dataList = Lists.newArrayList();

        houseMapForAllState.forEach(
                (houseStateEnum, houseData) -> houseData.sort((o1, o2) -> {
                    if (o1.getDealTime() == null || o2.getDealTime() == null){
                        return 0;
                    }
                    return o2.getDealTime().compareTo(o1.getDealTime());
                })
        );

        // 拼接表格的信息
        houseMapForAllState.forEach((houseStateEnum, houseData) -> {

            for (HouseData houseDatum : houseData) {
                if (!Objects.equals(houseDatum.getHouseState(), "yi_shou")){
                    continue;
                }

                if (!houseDatum.getCommunityName().startsWith("南海家园")){
                    continue;
                }

                List<String> oneRowDataList = new ArrayList<String>();
                oneRowDataList.add(houseDatum.getHouseCode());
                oneRowDataList.add(houseDatum.getCommunityName());
                oneRowDataList.add(houseDatum.getTitle());
                oneRowDataList.add(HouseStateEnum.getByCode(houseDatum.getHouseState()).getDesc());
                oneRowDataList.add(houseDatum.getArea().toString());
                oneRowDataList.add(houseDatum.getPriceStr());
//                oneRowDataList.add(houseDatum.getPriceStr());
                oneRowDataList.add(houseDatum.getUnitPriceStr());
                oneRowDataList.add(houseDatum.getDealTime());
                oneRowDataList.add("暂无");
                oneRowDataList.add(houseDatum.getFloorState());
                oneRowDataList.add(houseDatum.getTags());
                dataList.add(oneRowDataList);
            }
        });




        return generateTable(dataList);

    }

    public static String generateTable(List<List<String>> data) {
        StringBuilder table = new StringBuilder();
        // table head
        table.append("<table border=\"1\">");
        table.append("<thead>");
        table.append("<tr>");
        table.append("<th>房源编码</th>");
        table.append("<th>小区名称</th>");
        table.append("<th>标题</th>");
        table.append("<th>房源状态</th>");
        table.append("<th>房源面积</th>");
//        table.append("<th>挂牌价格</th>");
        table.append("<th>成交价格</th>");
        table.append("<th>成交单价</th>");
        table.append("<th>签约时间</th>");
        table.append("<th>成交周期</th>");
        table.append("<th>楼层信息</th>");
        table.append("<th>其它信息</th>");
        table.append("</tr>");
        table.append("</thead>");

        // table body
        table.append("<tbody>");
        for (List<String> row : data) {
            table.append("<tr>");
            for (String cell : row) {
                table.append("<td>").append(cell).append("</td>");
            }
            table.append("</tr>");
        }
        table.append("</tbody>");

        // table end
        table.append("</table>");
        return table.toString();
    }


}
