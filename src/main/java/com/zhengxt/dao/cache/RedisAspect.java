/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dao.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author ThinkPad
 */
@Aspect
@Component
public class RedisAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

//    @Before("execution(* com.zhengxt.service.*.find*(..))")
//    public Object findDataFromRedisCache(JoinPoint joinPoint) {
//        logger.info("------- findFromRedisCache ---------");
//        Object[] args = joinPoint.getArgs();
//        return null;
//    }
//
//    @After("execution(* com.zhengxt.service.*.find*(..))")
//    public Object saveDataToRedisCache(JoinPoint joinPoint) {
//        logger.info("------- findFromRedisCache ---------");
////        Object[] args = joinPoint.getTarget();
//        return new Object();
//    }

    @Around("execution(* com.zhengxt.service.*.find*(..))")
    public Object findDataByRedisCache(ProceedingJoinPoint joinPoint) {
        logger.info("----- findDataFromRedisCache ----- ");
        RedisDAO.setJedisConnectionFactory(jedisConnectionFactory);
        RedisDAO redis = new RedisDAO("findData");
        String key = makeRedisKey(joinPoint);
        Object result = redis.getObject(key);
        if (result == null) {
            try {
                result = joinPoint.proceed();
                logger.info("----- saveDataToRedisCache ----- ");
                redis.putObject(key, result);
            } catch (Throwable ex) {
                logger.error("proceed find method error", ex);
            }
        }
        return result;
    }

    /**
     * key规则，类+方法名+参数
     *
     * @param joinPoint
     * @return
     */
    public String makeRedisKey(JoinPoint joinPoint) {
        String key = joinPoint.getTarget() + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            key += String.valueOf(obj);
        }
        return key;
    }
}
