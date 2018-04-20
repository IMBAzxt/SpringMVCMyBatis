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
public class HelloWorldTest {
    @Autowired
    HelloWorld helloWorld;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSayHello() {
        System.out.println("sayHello");
        helloWorld.setWords("hello");
        helloWorld.sayHello();
    }

    @Test
    public void testMybatis() {
        Assert.assertEquals("zhang", userMapper.getUser(1).getUsername());
    }
}
