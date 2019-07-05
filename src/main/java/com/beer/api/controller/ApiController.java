package com.beer.api.controller;

import com.beer.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author beer
 * @date 2019/6/23
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("hello")
    public Object test() {
        return "Hello, I'm beer!";
    }

    @GetMapping("/daily/photo/{seq}")
    public Object getDailyPhoto(@PathVariable("seq") String seq) {
        return apiService.getDailyPhoto(seq);

    }

    @GetMapping("underLineToCamelCase")
    public Object underLineToCamelCase(@RequestParam String params) {
        return apiService.underLineToCamelCase(params);
    }
}
