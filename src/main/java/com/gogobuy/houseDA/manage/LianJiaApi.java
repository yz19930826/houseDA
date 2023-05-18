package com.gogobuy.houseDA.manage;

import com.gogobuy.houseDA.entity.HouseData;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface LianJiaApi {
    /**
     * 查询小区待售的房子，返回房子的唯一ID集合
     * @param communityName
     * @return
     */
    default List<String> communitySellingHouse(String communityName) throws IOException{
        throw new UnsupportedOperationException();
    }

    /**
     * 获取关注的房源信息
     * @return
     */
    default List<HouseData> queryFollowedHouseData(){
        throw new UnsupportedOperationException();
    }


    /**
     * 关注房源
     * @param houseCode
     * @return
     */
    default int followHouse(String houseCode){
        throw new UnsupportedOperationException();
    }

    /**
     * 取消关注房源
     * @param houseCode
     * @return
     */
    default int unFollowHouse(String houseCode){
        throw new UnsupportedOperationException();
    }


    /**
     * 查看房子详情
     * @param houseCode
     * @return
     */
    default HouseData houseDetail(String houseCode){
        throw new UnsupportedOperationException();
    }
}
