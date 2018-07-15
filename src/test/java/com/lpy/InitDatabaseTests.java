package com.lpy;

import com.lpy.dao.NewsDao;
import com.lpy.dao.UserDao;
import com.lpy.model.News;
import com.lpy.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by lipeiyuan on 2018/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Sql("/toutiao.sql")
public class InitDatabaseTests {

    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Test
    public void contextLoads() {

        Random random = new Random();
        for (int i = 0 ; i < 10 ; i++) {
            User user = new User();
            user.setHeadUrl("http://aaaa.html");
            user.setName("usr_"+i);
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);

            user.setPassword("newpsw");
            userDao.updatePassword(user);

            News news = new News();
            news.setCommentCount(i);
            news.setImage("http://aaaa"+i+".html");
            news.setLikeCount(i*2);
            news.setLink("abcde");
            news.setTitle(String.format("title{%d}",i));
            news.setCreatedDate(new Date(System.currentTimeMillis()));
            news.setUserId(user.getId());
            newsDao.addNews(news);
        }
        Assert.assertEquals("newpsw" , userDao.selectById(1).getPassword());
        userDao.deleteUserById(2);
        Assert.assertNull(userDao.selectById(2));
    }



}
