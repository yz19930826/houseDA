package com.gogobuy.houseDA.notice;

import com.gogobuy.houseDA.constants.HouseStateEnum;
import com.gogobuy.houseDA.entity.HouseData;

import java.util.List;
import java.util.Map;

public interface INotifier {
    void send(Map<HouseStateEnum, List<HouseData>> houseMapForAllState);
}
