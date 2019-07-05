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
 * @date 2019/6/30
 * @description photo
 */
@Service
public class PhotoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${daily.photo.url}")
    private String dailyPhotoUrl;

    public JSONObject getDailyPhotoFromBing(String seq) {
        String localUrl = MessageFormat.format(dailyPhotoUrl, seq);
        JSONObject jsonObject = restTemplate.getForObject(localUrl, JSONObject.class);
        Objects.requireNonNull(jsonObject, "获取图片失败");
        JSONObject imageJson = (JSONObject) jsonObject.getJSONArray("images").get(0);
        String url = imageJson.getString("url");
        String copyright = imageJson.getString("copyright");
        String imageUrl = "http://s.cn.bing.net/" + url;
        JSONObject resultJson = new JSONObject();
        resultJson.put("image_url", imageUrl);
        resultJson.put("copyright", copyright);
        resultJson.put("source", "https://cn.bing.com/");
        return resultJson;
    }
}
