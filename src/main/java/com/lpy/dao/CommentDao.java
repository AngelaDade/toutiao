package com.lpy.dao;

import com.lpy.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by lipeiyuan on 2018/7/23.
 */
@Mapper
public interface CommentDao {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id , content , create_date , entity_id , entity_type , status";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{userId}, #{content}, #{createDate}, #{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME ,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId , @Param("entityType") int entityType);


    @Select({"select count(id) from" , TABLE_NAME ,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    int getCommentCount(@Param("entityId") int entityId , @Param("entityType") int entityType);


}

