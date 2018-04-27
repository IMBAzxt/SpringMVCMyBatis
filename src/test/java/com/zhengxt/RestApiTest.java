/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 *
 * @author ThinkPad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring.xml",
    "classpath:springmvc-servlet.xml"})
@WebAppConfiguration
public class RestApiTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGet() throws Exception {

        String responseString = mockMvc.perform(
                get("/api/testGet") //请求地址
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) //返回的数据格式
        //                .param("id", "1")  //添加参数(可以添加多个)
        )
                .andExpect(status().isOk()) //判断返回是否成功
                .andDo(print()) //打印出请求信息
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("-----返回的json = " + responseString);

    }
}
