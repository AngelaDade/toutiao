package com.lpy.dao;

import com.lpy.model.Message;
import com.lpy.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lipeiyuan on 2018/7/26.
 */
@Mapper
public interface MessageDao {

    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content, create_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into " , TABLE_NAME ,"(", INSERT_FIELDS ,
            ") values (#{fromId},#{toId},#{content},#{createDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where conversation_id=#{conversationId}"})
    List<Message> selectAllByConversationId(@Param("conversationId") String conversationId);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where conversation_id=#{conversationId} order by id desc limit #{offset} , #{limit}"})
    List<Message> selectLimitedMessageByConversationId(@Param("conversationId") String conversationId , @Param("offset") int offset , @Param("limit") int limit);

}
