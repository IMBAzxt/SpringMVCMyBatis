/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dao.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 *
 * @author ThinkPad
 */
public class RedisDAO implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(RedisDAO.class);

    private static JedisConnectionFactory jedisConnectionFactory;

    private final String id;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisDAO.jedisConnectionFactory = jedisConnectionFactory;
    }

    public RedisDAO(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    /**
     * 将数据存入redis
     *
     * @param key 键值
     * @param value 数据
     */
    public void putObject(Object key, Object value) {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
        } catch (JedisConnectionException e) {
            logger.error("redis put object error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 将数据存入redis，可以设置缓存过期时间
     *
     * @param key 键值
     * @param value 数据
     * @param time 缓存过期时间
     */
    public void putObject(Object key, Object value, long time) {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), time);
        } catch (JedisConnectionException e) {
            logger.error("redis put object error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 通过键值获取redis中的数据
     *
     * @param key 键值
     * @return
     */
    public Object getObject(Object key) {
        Object result = null;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = serializer.deserialize(connection.get(serializer.serialize(key)));
        } catch (JedisConnectionException e) {
            logger.error("redis get object error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * 根据键值删除对应的数据
     *
     * @param key 键值
     * @return
     */
    public Object removeObject(Object key) {
        RedisConnection connection = null;
        Object result = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = connection.expire(serializer.serialize(key), 0);
        } catch (JedisConnectionException e) {
            logger.error("redis remove object error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * 清空redis，慎用
     */
    public void clear() {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            connection.flushDb();
            connection.flushAll();
        } catch (JedisConnectionException e) {
            logger.error("redis clear error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 获取redis缓存的大小
     *
     * @return
     */
    public int getSize() {
        int result = 0;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            result = Integer.valueOf(connection.dbSize().toString());
        } catch (JedisConnectionException e) {
            logger.error("redis get size error", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

}
