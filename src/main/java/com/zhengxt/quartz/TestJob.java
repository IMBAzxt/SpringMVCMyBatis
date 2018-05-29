/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.quartz;

import com.zhengxt.dao.cache.CacheAspect;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ThinkPad
 */
public class TestJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TestJob.class);

    public void doSomethingAgain() {
        System.err.println("u are the best!");
    }

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        logger.info("u are the best!");
    }

}
