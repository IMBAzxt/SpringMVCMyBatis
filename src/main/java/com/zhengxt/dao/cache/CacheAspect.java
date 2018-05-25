/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dao.cache;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author ThinkPad
 */
@Component
@Aspect
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Pointcut("@annotation(com.zhengxt.dao.cache.GetCache)")
    public void getCache() {
        logger.info("annotation @GetCache pointcut");
    }

    @Around(value = "getCache() && @annotation(getCache)")
    public Object findDataByRedisCache(ProceedingJoinPoint joinPoint, GetCache getCache) {
        logger.info("----- findDataFromRedisCache ----- ");
        RedisDAO.setJedisConnectionFactory(jedisConnectionFactory);
        RedisDAO redis = new RedisDAO("findData");
        String key = getCache.key();
        //如果注解指定key，使用注解的key，否则按照规则生成key
        if ("".equals(key)) {
            key = makeRedisKey(joinPoint);
        }
        Object result = redis.getObject(key);
        if (result == null) {
            try {
                result = joinPoint.proceed();
                logger.info("----- saveDataToRedisCache ----- ");
                //缓存过期时间逻辑处理
                long time = getCache.time();
                if (time == 0) {
                    redis.putObject(key, result);
                } else {
                    redis.putObject(key, result, time);
                }
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
        String key = joinPoint.getTarget().getClass().toString() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            key += String.valueOf(obj);
        }
        return key;
    }
}
