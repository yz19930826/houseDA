package com.gogobuy.houseDA.config;

import com.gogobuy.houseDA.utils.HeaderReader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.file.ClosedFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class HeaderConfig {



    @Bean
    public List<Pair<String, List<Header>>> appFollowQueryApiHeaders(){
        List<Pair<String, List<Header>>> loadList = HeaderReader.load("headers/app/");
        if (CollectionUtils.isEmpty(loadList)){
            throw new IllegalStateException("resources/heads/app下没有header文件");
        }

        for (Pair<String, List<Header>> pair : loadList) {
            List<Header> headers = pair.getValue();
            if (CollectionUtils.isEmpty(headers)){
                throw new IllegalStateException("resources/heads/app下存在head为空的文件，若不需要，则删除对应的txt文件");
            }
        }


        return loadList.stream()
                .sorted()
                .collect(Collectors.toList());
    }

}
