package com.beer.api.controller;

import com.beer.api.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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

    @PostMapping("upload/image")
    public Object uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam("description") String description,
                              @RequestParam("key") String key) {
        if (StringUtils.isEmpty(description)) {
            description = UUID.randomUUID().toString();
        }
        return apiService.uploadImage(file, description, key);
    }

    @GetMapping("jrebel")
    public Object getJrebelLicense() {
        return apiService.getJrebelLicense();
    }
}
