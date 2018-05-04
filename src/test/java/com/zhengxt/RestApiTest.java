/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhengxt.dto.Response;
import com.zhengxt.entity.Users;
import org.junit.Assert;
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
    int id = 1001;
    String username = "张三";

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGet() throws Exception {

        String result = mockMvc.perform(
                get("/api/testGet/" + id) //请求地址
                 .header("Accept-Encoding", "UTF-8") //添加头部信息
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) //返回的数据格式
                .param("username", username) //添加参数(可以添加多个)
        )
                .andDo(print()) //打印出请求信息
                .andExpect(status().isOk()) //判断返回是否成功
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.username").value(username))
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        // jackson字符串转对象
        ObjectMapper mapper = new ObjectMapper();

        Response<Users> resp = mapper.readValue(result, new TypeReference<Response<Users>>() {
        });
        Users users = resp.getData();
        Assert.assertEquals(String.valueOf(id), String.valueOf(users.getId()));
        Assert.assertEquals(username, users.getUsername());
    }

    @Test
    public void testPost() throws Exception {
        Users users = new Users();
        users.setId(id);
        users.setUsername(username);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                post("/api/testPost")
                //                                .flashAttr("users", users) //@ModelAttribute("users") Users users，这种情况需要使用flashAttr来进行传值
                .content(mapper.writeValueAsString(users))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.username").value(username))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void testPut() throws Exception {
        Users users = new Users();
        users.setId(id);
        users.setUsername(username);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                put("/api/testPut")
                .content(mapper.writeValueAsString(users))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.username").value(username))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete("/api/testDelete/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(id))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}
