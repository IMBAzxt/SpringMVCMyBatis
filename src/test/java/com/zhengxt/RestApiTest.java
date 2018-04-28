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

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGet() throws Exception {

        String result = mockMvc.perform(
                get("/api/testGet/1001") //�����ַ
                 .header("Accept-Encoding", "UTF-8") //���ͷ����Ϣ
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) //���ص����ݸ�ʽ
                .param("username", "zhangsan") //��Ӳ���(������Ӷ��)
        )
                .andExpect(status().isOk()) //�жϷ����Ƿ�ɹ�
                .andDo(print()) //��ӡ��������Ϣ
                .andReturn().getResponse().getContentAsString();   //����Ӧ������ת��Ϊ�ַ���
        // jackson�ַ���ת����
        ObjectMapper mapper = new ObjectMapper();

        Response<Users> resp = mapper.readValue(result, new TypeReference<Response<Users>>() {
        });
        Users users = resp.getData();
        Assert.assertEquals("1001", String.valueOf(users.getId()));
        Assert.assertEquals("zhangsan", users.getUsername());
    }
}
