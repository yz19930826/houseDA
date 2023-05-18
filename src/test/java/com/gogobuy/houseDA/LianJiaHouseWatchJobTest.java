package com.gogobuy.houseDA;


import com.gogobuy.houseDA.job.LianJiaHouseWatchJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class LianJiaHouseWatchJobTest {


    @Autowired
    LianJiaHouseWatchJob lianJiaHouseWatchJob;

    @Test
    public void processTest() throws IOException {
        lianJiaHouseWatchJob.process("南海家园");
    }
}
