/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.mapper;

import com.zhengxt.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author ThinkPad
 */
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{userId}")
//    @ResultMap("userMap")
    Users getUser(@Param("userId") int userId);

    Users findUserById(int id);

    void addUser(Users users);

    void updateUser(Users users);

    void deleteUser(int id);

    Users[] findAllUsers();
}
