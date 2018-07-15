package com.lpy.dao;

import com.lpy.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by lipeiyuan on 2018/7/10.
 */
@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    //需要注意在values的里面的属性需要与model里定义的属性相同，即用驼峰（如：headUrl），因为这个本质上引用了User的的get属性的方法（如getHeadUrl）
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where id=#{id}"})
    User selectById(int id);

    @Update({"update " , TABLE_NAME , " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from" , TABLE_NAME , " where id=#{id}"})
    void deleteUserById(int id);

    @Select({"select " , SELECT_FIELDS , "from" , TABLE_NAME , " where name=#{name}"})
    User selectByname(String name);

}
