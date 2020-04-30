package com.beer.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ToolService {

    @Resource
    private RestTemplate restTemplate;


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
        jsonObject.put("translate", translate(param));
    }

    public JSONObject translate(String param) {
        String url = "http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i={0}";
        ResponseEntity<JSONObject> resp = restTemplate.getForEntity(MessageFormat.format(url, param), JSONObject.class);
        if (resp.getStatusCode().value() == 200) {
            JSONArray jsonArray = (JSONArray) resp.getBody().getJSONArray("translateResult").get(0);
            return jsonArray.getJSONObject(0);
        }
        return null;
    }

    /**
     * timestamp format
     */
    public void timestampFormat(String param, JSONObject jsonObject) {
        long timestamp = Long.parseLong(param);
        if (param.length() == 13) {
            timestamp = timestamp / 1000;
        }

        Instant instant = Instant.ofEpochMilli(timestamp);
        //Asia/Shanghai
        ZoneId zone = ZoneId.of(ZoneId.SHORT_IDS.get("CTT"));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

        jsonObject.put("ShangHai", localDateTime.toString());

        zone = ZoneId.of("UTC");
        localDateTime = LocalDateTime.ofInstant(instant, zone);

        jsonObject.put("UTC", localDateTime.toString());
    }
}
