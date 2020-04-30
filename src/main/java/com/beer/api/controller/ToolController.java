package com.beer.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.beer.api.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("tool")
public class ToolController {

    @Resource
    private ToolService toolService;

    @GetMapping("{param}")
    public JSONObject tool(@PathVariable String param) {
        JSONObject jsonObject = toolService.main(param);
        if (jsonObject.keySet().isEmpty()) {
            jsonObject.put("error", "Beer can't understand");
        }
        return jsonObject;
    }
}
