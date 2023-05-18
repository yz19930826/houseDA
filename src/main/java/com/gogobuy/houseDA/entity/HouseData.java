package com.gogobuy.houseDA.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 炎族族长炎天帝
 * @since 2023-04-24
 */
@TableName("t_house_data")
public class HouseData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String houseCode;

    private String houseState;

    private String houseType;

    private Integer price;

    private Integer unitPrice;

    private String title;

    private String dealTime;

    private String orientation;

    private String floorState;

    private String homePic;

    private String communityId;

    private String communityName;

    private String tags;

    private Integer cityId;

    private Integer hallNum;

    private Integer bedroomNum;

    private String houseLayout;

    private String circleName;

    private Double area;

    private String priceStr;

    private String unitPriceStr;

    private Integer dealPrice;

    private String dealPriceStr;

    private Integer dealUnitPrice;

    private String dealUnitPriceStr;

    private String allData;

    private String createdTime;

    private String modifyTime;

    private String insideArea;

    private String elevator;

    private String guapaiTime;

    private String lastTransactionTime;

    private String houseAgeAfterLastTransaction;

    private String coreSellPoint;

    private String communityDesc;

    private String suitablePopulation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouseState() {
        return houseState;
    }

    public void setHouseState(String houseState) {
        this.houseState = houseState;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getFloorState() {
        return floorState;
    }

    public void setFloorState(String floorState) {
        this.floorState = floorState;
    }

    public String getHomePic() {
        return homePic;
    }

    public void setHomePic(String homePic) {
        this.homePic = homePic;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getHallNum() {
        return hallNum;
    }

    public void setHallNum(Integer hallNum) {
        this.hallNum = hallNum;
    }

    public Integer getBedroomNum() {
        return bedroomNum;
    }

    public void setBedroomNum(Integer bedroomNum) {
        this.bedroomNum = bedroomNum;
    }

    public String getHouseLayout() {
        return houseLayout;
    }

    public void setHouseLayout(String houseLayout) {
        this.houseLayout = houseLayout;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getUnitPriceStr() {
        return unitPriceStr;
    }

    public void setUnitPriceStr(String unitPriceStr) {
        this.unitPriceStr = unitPriceStr;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealPriceStr() {
        return dealPriceStr;
    }

    public void setDealPriceStr(String dealPriceStr) {
        this.dealPriceStr = dealPriceStr;
    }

    public Integer getDealUnitPrice() {
        return dealUnitPrice;
    }

    public void setDealUnitPrice(Integer dealUnitPrice) {
        this.dealUnitPrice = dealUnitPrice;
    }

    public String getDealUnitPriceStr() {
        return dealUnitPriceStr;
    }

    public void setDealUnitPriceStr(String dealUnitPriceStr) {
        this.dealUnitPriceStr = dealUnitPriceStr;
    }

    public String getAllData() {
        return allData;
    }

    public void setAllData(String allData) {
        this.allData = allData;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getInsideArea() {
        return insideArea;
    }

    public void setInsideArea(String insideArea) {
        this.insideArea = insideArea;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getGuapaiTime() {
        return guapaiTime;
    }

    public void setGuapaiTime(String guapaiTime) {
        this.guapaiTime = guapaiTime;
    }

    public String getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(String lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
    }

    public String getHouseAgeAfterLastTransaction() {
        return houseAgeAfterLastTransaction;
    }

    public void setHouseAgeAfterLastTransaction(String houseAgeAfterLastTransaction) {
        this.houseAgeAfterLastTransaction = houseAgeAfterLastTransaction;
    }

    public String getCoreSellPoint() {
        return coreSellPoint;
    }

    public void setCoreSellPoint(String coreSellPoint) {
        this.coreSellPoint = coreSellPoint;
    }

    public String getCommunityDesc() {
        return communityDesc;
    }

    public void setCommunityDesc(String communityDesc) {
        this.communityDesc = communityDesc;
    }

    public String getSuitablePopulation() {
        return suitablePopulation;
    }

    public void setSuitablePopulation(String suitablePopulation) {
        this.suitablePopulation = suitablePopulation;
    }

    @Override
    public String toString() {
        return "HouseData{" +
            "id = " + id +
            ", houseCode = " + houseCode +
            ", houseState = " + houseState +
            ", houseType = " + houseType +
            ", price = " + price +
            ", unitPrice = " + unitPrice +
            ", title = " + title +
            ", dealTime = " + dealTime +
            ", orientation = " + orientation +
            ", floorState = " + floorState +
            ", homePic = " + homePic +
            ", communityId = " + communityId +
            ", communityName = " + communityName +
            ", tags = " + tags +
            ", cityId = " + cityId +
            ", hallNum = " + hallNum +
            ", bedroomNum = " + bedroomNum +
            ", houseLayout = " + houseLayout +
            ", circleName = " + circleName +
            ", area = " + area +
            ", priceStr = " + priceStr +
            ", unitPriceStr = " + unitPriceStr +
            ", dealPrice = " + dealPrice +
            ", dealPriceStr = " + dealPriceStr +
            ", dealUnitPrice = " + dealUnitPrice +
            ", dealUnitPriceStr = " + dealUnitPriceStr +
            ", allData = " + allData +
            ", createdTime = " + createdTime +
            ", modifyTime = " + modifyTime +
            ", insideArea = " + insideArea +
            ", elevator = " + elevator +
            ", guapaiTime = " + guapaiTime +
            ", lastTransactionTime = " + lastTransactionTime +
            ", houseAgeAfterLastTransaction = " + houseAgeAfterLastTransaction +
            ", coreSellPoint = " + coreSellPoint +
            ", communityDesc = " + communityDesc +
            ", suitablePopulation = " + suitablePopulation +
        "}";
    }
}
