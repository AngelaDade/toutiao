package com.lpy.dao;

import com.lpy.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lipeiyuan on 2018/7/10.
 */
@Mapper
public interface NewsDao {

    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, user_id, created_date ";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into " , TABLE_NAME ,"(", INSERT_FIELDS ,
            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{userId},#{createdDate})"})
    int addNews(News news);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId , @Param("offset") int offset , @Param("limit") int limit);
}