package com.beer.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author beer
 * @date 2019/6/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;


    @Test
    public void getDailyPhoto() {
        apiService.getDailyPhoto("1");
    }
}