/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.quartz;

import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author ThinkPad
 */
public class Test {
    public static void main(String[] args) throws SchedulerException {
        //1.����Scheduler�Ĺ���
        SchedulerFactory sf = new StdSchedulerFactory();
        //2.�ӹ����л�ȡ������ʵ��
        Scheduler scheduler = sf.getScheduler();

        //3.����JobDetail
        JobDetail jb = JobBuilder.newJob(TestJob.class)
                .withDescription("this is a ram job") //job������
                .withIdentity("ramJob", "ramGroup") //job ��name��group
                .build();

        //�������е�ʱ�䣬SimpleSchedle���ʹ�������Ч
        long time = System.currentTimeMillis() + 3 * 1000L; //3�����������
        Date statTime = new Date(time);

        //4.����Trigger
        //ʹ��SimpleScheduleBuilder����CronScheduleBuilder
        Trigger t = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("ramTrigger", "ramTriggerGroup")
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .startAt(statTime) //Ĭ�ϵ�ǰʱ������
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) //����ִ��һ��
                .build();

        //5.ע������Ͷ�ʱ��
        scheduler.scheduleJob(jb, t);

        //6.���� ������
        scheduler.start();
    }
}
