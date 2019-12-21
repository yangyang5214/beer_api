package com.beer.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.scripts.JS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ToolService {

    /**
     * 主函数
     */
    public JSONObject main(String param) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNumeric(param)) {
            timestampFormat(param, jsonObject);
            return jsonObject;
        }
        stringFormat(param, jsonObject);
        return jsonObject;
    }


    /**
     * string format
     */
    public void stringFormat(String param, JSONObject jsonObject) {
        jsonObject.put("upper", param.toUpperCase());
        jsonObject.put("lower", param.toLowerCase());
    }

    /**
     * timestamp format
     */
    public void timestampFormat(String param, JSONObject jsonObject) {
        if (param.length() == 10) {
            param = param + "000";
        } else {
            return;
        }

        Instant instant = Instant.ofEpochMilli(Long.parseLong(param));
        //Asia/Shanghai
        ZoneId zone = ZoneId.of(ZoneId.SHORT_IDS.get("CTT"));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

        jsonObject.put("ShangHai", localDateTime.toString());

        zone = ZoneId.of("UTC");
        localDateTime = LocalDateTime.ofInstant(instant, zone);

        jsonObject.put("UTC", localDateTime.toString());
    }
}
