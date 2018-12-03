package com.lpy.controller;

import com.lpy.intercepter.PassportInterceptor;
import com.lpy.service.NewsService;
import com.lpy.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lipeiyuan on 2018/7/22.
 */
@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @RequestMapping(value = {"/uploadImage"} , method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJsonString(500 , "上传图片失败");
            }
            return ToutiaoUtil.getJsonString(200,fileUrl);

        } catch (IOException e) {
            logger.error("上传图片失败"+e.getMessage());
            return ToutiaoUtil.getJsonString(500,"上传图片失败");
        }
    }

    //展示图片
    @RequestMapping(value = {"/image"} , method = RequestMethod.GET)
    @ResponseBody
    public void getImage(@RequestParam(value = "name") String imageName , HttpServletResponse response) {

        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR+imageName)) , response.getOutputStream());
        } catch (IOException e) {
            logger.error("读取图片错误：" + e.getMessage());
        }


    }





}
