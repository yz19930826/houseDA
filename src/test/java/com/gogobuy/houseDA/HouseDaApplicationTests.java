package com.gogobuy.houseDA;

import com.gogobuy.houseDA.entity.User;
import com.gogobuy.houseDA.mapper.UserMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HouseDaApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
//		User user = new User();
//		user.setId(2L);
//		user.setName("炎族族长炎天帝");
//		user.setAge(20);
//		user.setEmail("yanzhansisi@gmail.com");
//		int insert = userMapper.insert(user);
//		System.out.println(insert);
	}

}
