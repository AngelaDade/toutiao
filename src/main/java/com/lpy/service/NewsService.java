package com.lpy.service;

import com.lpy.dao.NewsDao;
import com.lpy.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
