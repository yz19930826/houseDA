package com.gogobuy.houseDA.service;

import com.gogobuy.houseDA.entity.HouseData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 炎族族长炎天帝
 * @since 2023-04-24
 */
public interface IHouseDataService extends IService<HouseData> {

    Map<String, HouseData> toMap(List<String> houseDataList);

    HouseData queryHouseDataByHouseCode(String houseCode);

    void updateHouseDataByHouseCode(HouseData data);

    List<HouseData> queryAll();
}
