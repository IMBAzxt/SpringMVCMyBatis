/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ThinkPad
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:spring.xml")
public class HelloWorldTest {
//    @Autowired

    @Test
    public void testSayHello() {
        System.out.println("sayHello");
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring.xml"));
        HelloWorld instance = (HelloWorld) bf.getBean("helloWorld");
        instance.setWords("hello");
        instance.sayHello();
    }

}
