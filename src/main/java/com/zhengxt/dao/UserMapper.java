/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dao;

import com.zhengxt.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author ThinkPad
 */
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{userId}")
    Users getUser(@Param("userId") String userId);
}
