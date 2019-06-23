package com.beer.api.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author beer
 * @date 2019/6/23
 */
@Component
@Aspect
public class WebServiceAspect {

    @Around("execution(* com.beer.api.controller.*.*(..))")
    public JSONObject around(ProceedingJoinPoint joinPoint) {
        JSONObject jsonObject = new JSONObject();
        Object data;
        try {
            data = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            jsonObject.put("message", throwable.getMessage());
            jsonObject.put("status", "failed");
            return jsonObject;
        }

        jsonObject.put("data", data);
        jsonObject.put("status", "success");
        return jsonObject;
    }
}
