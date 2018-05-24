/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.service;

import com.zhengxt.dao.cache.RedisAspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author ThinkPad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring.xml",
    "classpath:springmvc-servlet.xml"})
@WebAppConfiguration
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    UserService userService;

    @Test
    public void testFindUsersById(){
        logger.info(String.valueOf(userService.findUsersById(1)));
    }

    public void testAddUsers() {
    }
}
