package com.gogobuy.houseDA;

import com.gogobuy.houseDA.constants.LianJiaProperties;
import com.gogobuy.houseDA.entity.HouseData;
import com.gogobuy.houseDA.entity.User;
import com.gogobuy.houseDA.manage.LianJiaAppApi;
import com.gogobuy.houseDA.manage.LianJiaWebApi;
import com.gogobuy.houseDA.mapper.UserMapper;
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

		String appCookie = lianJiaProperties.getAppCookie();
		System.out.println(appCookie);
	}

	@Test
	public void queryFollowedHouseData(){
		List<HouseData> houseData = lianJiaAppApi.queryFollowedHouseData("lianjia_uuid=5D10EBCB-7CCA-4459-9095-41C70D78F6E9; lianjia_ssid=F282986C-95C3-405A-95E6-F1EC230FF5E7; lianjia_udid=00000000-0000-0000-0000-000000000000; lianjia_token=2.00125626c26e47600903fb0ff34067f051; latitude=(null); longitude=(null)");
		System.out.println(houseData);
	}


}
