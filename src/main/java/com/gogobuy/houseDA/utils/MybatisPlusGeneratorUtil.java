package com.gogobuy.houseDA.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class MybatisPlusGeneratorUtil {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:sqlite:/Users/yanzhan/Library/Mobile Documents/com~apple~CloudDocs/Mine/WorkSpace/Mine/houseDA/housedata.sqlite", "", "")
                .globalConfig(builder -> {
                    builder.author("炎族族长炎天帝") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride()// 覆盖已生成文件
                            .outputDir("/Users/yanzhan/Library/Mobile Documents/com~apple~CloudDocs/Mine/WorkSpace/Mine/houseDA/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.gogobuy.houseDA") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_house_data") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .entityBuilder().enableFileOverride()
                            .serviceBuilder().enableFileOverride()
                            .mapperBuilder().enableFileOverride();// 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
