package com.beer.api.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
    private PhotoService photoService;

    /**
     * 获取每日图片
     * <p>
     * https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1
     * 通过 bing 的接口
     */
    public JSONObject getDailyPhoto(String seq) {
        return photoService.getDailyPhotoFromBing(seq);
    }


    /**
     * 下划线转驼峰
     * <p>
     * params 参数
     */
    public String underLineToCamelCase(String params) {
        if (StringUtils.isEmpty(params)) {
            return params;
        }
        //先根据 ; 分割
        String[] strings = params.split(";");
        StringBuilder stringBuilder = new StringBuilder();

        //循环处理
        for (String string : strings) {
            int index = string.indexOf("_");

            //不需要转换的
            if (index == -1) {
                stringBuilder.append(string);
                continue;
            }
            String targetStr = string.substring(index + 1, index + 2);
            targetStr = targetStr.toUpperCase();
            stringBuilder.append(string, 0, index);
            stringBuilder.append(targetStr);
            stringBuilder.append(string.substring(index + 2));
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }
}
