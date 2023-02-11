package com.gogobuy.houseDA;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gogobuy.houseDA.mapper")
public class HouseDaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseDaApplication.class, args);
	}

}
