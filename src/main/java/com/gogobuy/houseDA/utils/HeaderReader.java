package com.gogobuy.houseDA.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Header读取类
 * 写这个类的目的是为了读取从抓包工具复制来的header数据，格式为key:value的形式
 */
public class HeaderReader {

    /**
     * 加载文件中的header数据，一次性可以加载多个文件
     * @param directoryPath 文件夹路径
     * @return Pair是k,v结构
     */
    public static List<Pair<String,List<Header>>> load(String directoryPath){

        if (StringUtils.isEmpty(directoryPath)){
            throw new IllegalArgumentException("directoryPath is empty");
        }

        List<Pair<String,List<Header>>> resultList = Lists.newArrayList();
        // 获取文件夹下的所有文件信息
        for (File file : FileUtil.ls(directoryPath)) {
            // 加载文件内容
            FileReader fileReader = new FileReader(file);
            List<String> lines = fileReader.readLines();

            List<Header> collect = lines.stream()
                    .map((Function<String, Header>) s -> {
                        int i = s.indexOf(":");
                        if (i < 0){
                            return null;
                        }

                        String key = s.substring(0, i);
                        String value = s.substring(i + 1);

                        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
                            return null;
                        }
                        return new BasicHeader(key, value);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            resultList.add(Pair.of(file.getName(),collect));
        }
        return resultList;
    }

    public static void main(String[] args) throws IOException {

        Resource resource = new ClassPathResource("headers");


        List<Pair<String, List<Header>>> load = load(resource.getURI().getPath());

        System.out.println();

    }
}
