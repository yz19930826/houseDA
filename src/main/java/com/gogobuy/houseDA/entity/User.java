package com.gogobuy.houseDA.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
//@TableName("user")
public class User {
//    @TableId
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
