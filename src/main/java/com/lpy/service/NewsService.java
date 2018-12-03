package com.lpy.service;

import com.lpy.dao.NewsDao;
import com.lpy.model.News;
import com.lpy.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by lipeiyuan on 2018/7/14.
 */
@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    public List<News> getLatestNews(int userId , int offset , int limit) {
        return newsDao.selectByUserIdAndOffset(userId , offset , limit);
    }

    //图片保存到本地
    public String saveImage(MultipartFile file) throws IOException {
        //先判断文件是不是图片（根据文件扩展名判断 从后缀判断）
        int doPos = file.getOriginalFilename().lastIndexOf(".");

        if (doPos < 0) {
            return null;
        }

        String fileExt = file.getOriginalFilename().substring(doPos+1).toLowerCase();

        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }

        //条件符合存放到本地服务器

        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

        return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;

    }

    public void updateCommentCount(int commentCount , int newsId) {
        newsDao.updateCommentCount(commentCount,newsId);
    }

    public News getById(int newId) {
        return newsDao.selectById(newId);
    }

}
