package com.beer.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.beer.api.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tool")
public class ToolController {

    @Autowired
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
