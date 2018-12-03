package com.lpy.dao;

import com.lpy.model.News;
import com.lpy.model.User;
import org.apache.ibatis.annotations.*;

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

    @Update({"update ",TABLE_NAME , " set comment_count=#{commentCount} where id=#{id}"})
    void updateCommentCount(@Param("commentCount") int commentCount , @Param("id") int id);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where id=#{id}"})
    News selectById(int id);
}
