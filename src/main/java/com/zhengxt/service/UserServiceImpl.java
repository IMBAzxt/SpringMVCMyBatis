/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.service;

import com.zhengxt.dao.cache.RedisDAO;
import com.zhengxt.entity.Users;
import com.zhengxt.mapper.UserMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author ThinkPad
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public Users findUsersById(int id) {
        return userMapper.findUserById(id);
    }

    public void addUsers(Users users) {
        userMapper.addUser(users);
    }

}
