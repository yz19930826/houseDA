package com.gogobuy.houseDA.manage;

import com.alibaba.fastjson.JSON;
import com.gogobuy.houseDA.entity.HouseData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LianJiaWebApiTest {


    @Autowired
    private LianJiaWebApi lianJiaWebApi;

    @Test
    void communitySellingHouse() throws IOException {

        lianJiaWebApi.communitySellingHouse("南海家园");
    }

    @Test
    void houseDetail() throws IOException {
        HouseData data = lianJiaWebApi.houseDetail("101118050850");
        System.out.println(JSON.toJSONString(data));
    }
}