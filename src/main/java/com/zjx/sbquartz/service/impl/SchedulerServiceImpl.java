package com.zjx.sbquartz.service.impl;

import com.zjx.sbquartz.service.HelloJob;
import com.zjx.sbquartz.service.SchedulerService;
import com.zjx.sbquartz.util.VoiceUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService{

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    //新增任务
    public boolean addCronJob(String cron){
        String jobName = "job";
        String jobGroup = "group";
        //springboot中是schedulerFactoryBean
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail != null){
                System.out.println("job已存在");
            }else {
                //创建job信息
                jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity(jobName,jobGroup).build();
                //用jopData来传递数据
                jobDetail.getJobDataMap().put(jobName,HelloJob.class.getName());

                //表达式调度构建器
                //校验cron表达式
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

                //按新的cronException表达式构建一个新的triger
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName+"_trigger",jobGroup+"_trigger").withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail,trigger);
            }
        } catch (SchedulerException e) {
            return false;
        }
        VoiceUtil.printVoice("定时任务新增成功，请去控制台查看");
        return true;
    }

    //修改任务
    public boolean updateJob(String cron){
        try {
            String jobName = "job";
            String jobGroup = "group";
            //springboot中是schedulerFactoryBean
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName+"_trigger",jobGroup+"_trigger");
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            trigger = trigger.getTriggerBuilder().
                    withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey,trigger);
        } catch (SchedulerException e) {
            return false;
        }
        VoiceUtil.printVoice("定时任务更改成功，请去控制台查看");
        return true;
    }

    //暂停任务
    public boolean pauseJob(){
        try {
            String jobName = "job";
            String jobGroup = "group";
            //springboot中是schedulerFactoryBean
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName+"_trigger",jobGroup+"_trigger");

            scheduler.pauseTrigger(triggerKey);
            System.out.println("==============pause job");
        } catch (SchedulerException e) {
            return false;
        }
        VoiceUtil.printVoice("定时任务已暂停，请去控制台查看");
        return true;
    }

    //恢复任务
    public boolean resumeJob(){
        try {
            String jobName = "job";
            String jobGroup = "group";
            //springboot中是schedulerFactoryBean
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName+"_trigger",jobGroup+"_trigger");
            scheduler.resumeTrigger(triggerKey);
            System.out.println("resume job");
        } catch (SchedulerException e) {
            return false;
        }
        VoiceUtil.printVoice("定时任务已恢复，请去控制台查看");
        return true;
    }


    //删除任务
    public  boolean deleteJob(){
        try {
            String jobName = "job";
            String jobGroup = "group";
            //springboot中是schedulerFactoryBean
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
            scheduler.deleteJob(jobKey);
            System.out.println("delete job");
        } catch (SchedulerException e) {
            return false;
        }
        VoiceUtil.printVoice("定时任务已删除，请去控制台查看");
        return true;
    }

    //定时任务是否存在
    public boolean isExist() {
        try {
            String jobName = "job";
            String jobGroup = "group";
            //springboot中是schedulerFactoryBean
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail==null){
                return false;
            }
        } catch (SchedulerException e) {
        }
        return true;
    }


}
