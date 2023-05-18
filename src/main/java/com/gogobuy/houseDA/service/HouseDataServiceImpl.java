package com.gogobuy.houseDA.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.mapper.HouseDataMapper;
import com.gogobuy.houseDA.service.IHouseDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 炎族族长炎天帝
 * @since 2023-04-24
 */
@Service
public class HouseDataServiceImpl extends ServiceImpl<HouseDataMapper, HouseData> implements IHouseDataService {

    @Override
    public Map<String, HouseData> toMap(List<String> houseCodeList) {
        QueryWrapper<HouseData> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("house_code", houseCodeList);
        return list(queryWrapper).stream()
                .collect(Collectors.toMap(HouseData::getHouseCode, houseData -> houseData));
    }

    @Override
    public HouseData queryHouseDataByHouseCode(String houseCode) {
        QueryWrapper<HouseData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_code", houseCode);
        return getOne(queryWrapper);
    }

    @Override
    public void updateHouseDataByHouseCode(HouseData data) {
        UpdateWrapper<HouseData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("house_code", data.getHouseCode());
        update(data, updateWrapper);
    }

    @Override
    public List<HouseData> queryAll() {
        return list();
    }
}
