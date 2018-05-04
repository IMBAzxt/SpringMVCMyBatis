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
    String username = "����";

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGet() throws Exception {

        String result = mockMvc.perform(
                get("/api/testGet/" + id) //�����ַ
                 .header("Accept-Encoding", "UTF-8") //���ͷ����Ϣ
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) //���ص����ݸ�ʽ
                .param("username", username) //��Ӳ���(������Ӷ��)
        )
                .andDo(print()) //��ӡ��������Ϣ
                .andExpect(status().isOk()) //�жϷ����Ƿ�ɹ�
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.username").value(username))
                .andReturn().getResponse().getContentAsString();   //����Ӧ������ת��Ϊ�ַ���
        // jackson�ַ���ת����
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
                //                                .flashAttr("users", users) //@ModelAttribute("users") Users users�����������Ҫʹ��flashAttr�����д�ֵ
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
