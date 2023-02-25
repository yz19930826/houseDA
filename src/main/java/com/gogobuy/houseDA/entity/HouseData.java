package com.gogobuy.houseDA.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HouseData {
    /**
     * 面积大小
     */
    private BigDecimal area;
    /**
     * 区域名称
     */
    private String circleName;
    /**
     * 房屋的数量
     */
    private Integer bedroomNum;

    /**
     * 大厅数
     */
    private Integer hallNum;

    /**
     * 城市ID
     */
    private Integer cityId;

    /**
     * 标签 多个以竖线分割开
     */
    private String tags;

    private String communityId;

    private String communityName;

    private String homePic;

    private String floorState;

    private String houseFollowedAmount;

    private String houseCode;

    private String houseState;

    private String houseType;

    private Integer price;

    private Integer unitPrice;

    private String orientation;

    private String title;

    private String dealTime;
}
