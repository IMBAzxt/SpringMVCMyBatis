/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.service;

import com.zhengxt.entity.Users;
import org.springframework.cache.annotation.Cacheable;

/**
 *
 * @author ThinkPad
 */

public interface UserService {

    Users findUsersById(int id);

    void addUsers(Users users);

}
