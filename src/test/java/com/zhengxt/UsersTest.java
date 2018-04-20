/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt;

import com.zhengxt.dao.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ThinkPad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring.xml")
public class UsersTest {

    @Autowired
    UserMapper userMapper;
    static int userId = 0;

    @Test
    public void testAddUser() {
        Users users = new Users();
        users.setUsername("test001");
        users.setPassword("password");
        users.setPasswordSalt("salt");
        userMapper.addUser(users);
        userId = users.getId();
    }

    @Test
    public void testFindUser() {
        Assert.assertEquals("test001", userMapper.findUserById(userId).getUsername());
    }

    @Test
    public void testFindAllUser() {
        Users[] users = userMapper.findAllUsers();
        System.out.println(users.length);
    }

    @Test
    public void testUpdateUser() {
        Users users = userMapper.findUserById(userId);
        users.setPassword("passwd");
        userMapper.updateUser(users);
        Assert.assertEquals("passwd", userMapper.findUserById(userId).getPassword());
    }


    @Test
    public void testDeleteUser() {
        userMapper.deleteUser(userId);
        Assert.assertEquals(null, userMapper.findUserById(userId));
    }
}
