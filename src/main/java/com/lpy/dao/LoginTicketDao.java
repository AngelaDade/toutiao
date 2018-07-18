package com.lpy.dao;

import com.lpy.model.LoginTicket;
import com.lpy.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by lipeiyuan on 2018/7/10.
 */
@Mapper
public interface LoginTicketDao {

    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, expired, ticket, status ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    //需要注意在values的里面的属性需要与model里定义的属性相同，即用驼峰（如：headUrl），因为这个本质上引用了User的的get属性的方法（如getHeadUrl）
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{userId}, #{expired}, #{ticket}, #{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME , " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("status") int status , @Param("ticket") String ticket);
}
