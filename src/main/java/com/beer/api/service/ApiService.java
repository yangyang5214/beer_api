package com.beer.api.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

/**
 * @author beer
 * @date 2019/6/23
 * @description ApiService
 */
@Service
public class ApiService {


    @Autowired
    private PhotoService photoService;


    @Autowired
    private COSClient cosClient;

    @Value("${cos.bucket.name}")
    private String bucketName;

    @Value("${cos.upload.key}")
    private String uploadKey;


    /**
     * markdown image 的语法
     */
    private final String IMAGE_MARKDOWN = "![{0}]({1})";

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

    public String uploadImage(MultipartFile file, String description, String key) {
        if (!Objects.equals(uploadKey, key)) {
            throw new IllegalArgumentException("暗号错误");
        }
        try {
            String fileName = file.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            final File tempFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
            file.transferTo(tempFile);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, description + "." + prefix, tempFile);
            cosClient.putObject(putObjectRequest);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            String url = "https://beer-1256523277.cos.ap-shanghai.myqcloud.com/" + description + "." + prefix;
            String localMkImage = IMAGE_MARKDOWN;
            return MessageFormat.format(localMkImage, description, url);
        } catch (CosServiceException serverException) {
            throw new RuntimeException("CosServiceException: " + serverException.getMessage());
        } catch (CosClientException clientException) {
            throw new RuntimeException("clientException: " + clientException.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e.getMessage());
        }
    }
}
