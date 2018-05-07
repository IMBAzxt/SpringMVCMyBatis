/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dao.cache;

import static org.junit.Assert.*;//±ÿ–Î «static
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ThinkPad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RedisDAOTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisDAOTest.class);
    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    RedisDAO redis;

    @Before
    public void setup() {
        RedisDAO.setJedisConnectionFactory(jedisConnectionFactory);
        redis = new RedisDAO("111");
    }

    @Test
    public void testPutObject() {
        redis.putObject("001", "this is a test message!");
    }

    @Test
    public void testGetObject() {
        String result = String.valueOf(redis.getObject("001"));
        assertEquals("this is a test message!", result);
        logger.info(result);
    }

    @Test
    public void testGetSize() {
        logger.info("redis size:" + redis.getSize());
    }

    @Test
    public void testRemoveObject() {
        assertNotNull(redis.removeObject("001"));
        assertNotEquals("this is a test message!", redis.getObject("001"));
    }

    @Test
    public void testClear() {
        redis.clear();
        logger.info("redis size:" + redis.getSize());
    }


}
