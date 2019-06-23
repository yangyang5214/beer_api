package com.beer.api.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author beer
 * @date 2019/6/23
 * @description ApiService
 */
@Service
public class ApiService {


    @Autowired
    private RestTemplate restTemplate;


    @Value("${daily.photo.url}")
    private String dailyPhotoUrl;


    /**
     * 获取每日图片
     * <p>
     * https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1
     * 通过 bing 的接口
     */
    public String getDailyPhoto(String seq) {
        dailyPhotoUrl = MessageFormat.format(dailyPhotoUrl, seq);
        JSONObject jsonObject = restTemplate.getForObject(dailyPhotoUrl, JSONObject.class);
        Objects.requireNonNull(jsonObject, "获取图片失败");
        JSONObject imageJson = (JSONObject) jsonObject.getJSONArray("images").get(0);
        String url = imageJson.getString("url");
        String imageUrl = "http://s.cn.bing.net/" + url;
        return imageUrl;
    }
}
