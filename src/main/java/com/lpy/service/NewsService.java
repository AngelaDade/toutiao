package com.lpy.service;

import com.lpy.dao.NewsDao;
import com.lpy.model.News;
import com.lpy.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        String fileExt = file.getOriginalFilename().substring(doPos+1);

        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }

        //条件符合存放到本地服务器

    }

}
