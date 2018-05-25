/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.service;

import com.zhengxt.dao.cache.GetCache;
import com.zhengxt.entity.Users;
import com.zhengxt.mapper.UserMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ThinkPad
 */
@Service
public class UserServiceImpl implements UserService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @GetCache(key = "id")
    public Users findUsersById(int id) {
        Users users = userMapper.findUserById(id);
        logger.info(users.toString());
        return users;
    }

    public void addUsers(Users users) {
        userMapper.addUser(users);
    }

}
