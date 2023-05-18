package com.gogobuy.houseDA;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gogobuy.houseDA.constants.LianJiaProperties;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.entity.User;
import com.gogobuy.houseDA.manage.LianJiaAppApi;
import com.gogobuy.houseDA.manage.LianJiaWebApi;
import com.gogobuy.houseDA.mapper.UserMapper;
import com.gogobuy.houseDA.service.IHouseDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class HouseDaApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    LianJiaWebApi lianJiaWebApi;

    @Autowired
    LianJiaAppApi lianJiaAppApi;

    @Autowired
    private LianJiaProperties lianJiaProperties;

    @Autowired
    private IHouseDataService houseDataService;

    @Test
    void contextLoads() {

//		User user = new User();
//		user.setName("炎族族长炎天帝");
//		user.setAge(20);
//		user.setEmail("yanzhansisi@gmail.com");
//		int insert = userMapper.insert(user);
//		System.out.println(insert);

        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void dummy() throws IOException {
//		List<String> 南海家园 = lianJiaWebApi.communitySellingHouse("南海家园");
//		System.out.println(南海家园);

//		String appCookie = lianJiaProperties.getAppCookie();
//		System.out.println(appCookie);
    }

    @Test
    public void queryFollowedHouseData() {
        List<HouseData> houseData = lianJiaAppApi.queryFollowedHouseData();
        System.out.println(houseData);
    }

    @Test
    public void followHouseTest() {
        lianJiaWebApi.followHouse("101117888818");
    }

    // unfollowHouseTest
    @Test
    public void unfollowHouseTest() {
        int i = lianJiaWebApi.unFollowHouse("101116573908");
        System.out.println(i);
    }

    @Test
    public void saveFollowedHouseDataToDb() {
        List<HouseData> houseData = lianJiaAppApi.queryFollowedHouseData();
        for (HouseData data : houseData) {
            String houseState = data.getHouseState();
            if (houseState.equals("yi_shou")) {
                data.setDealPrice(data.getPrice());
                data.setDealPriceStr(data.getPriceStr());
                data.setDealUnitPrice(data.getUnitPrice());
                data.setDealUnitPriceStr(data.getUnitPriceStr());
				UpdateWrapper<HouseData> houseDataUpdateWrapper = new UpdateWrapper<>();
				houseDataUpdateWrapper.eq("house_code", data.getHouseCode());
				houseDataService.update(data,houseDataUpdateWrapper);
            }
        }
    }


}
