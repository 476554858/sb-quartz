package com.zjx.sbquartz.config;

import com.zjx.sbquartz.entity.CronEntity;
import com.zjx.sbquartz.mapper.CronEntityMapper;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {

    @Autowired
    CronEntityMapper cronEntityMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println("执行另一个定时任务======================"+sdf.format(new Date()));
                    }
                },
                //2.设置执行周期(Trigger)
                new Trigger() {
                    @Transactional
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        //2.1 从数据库获取执行周期
                        CronEntity cronEntity =  cronEntityMapper.selectById(1);
                      /*  String value = cronEntity.getValue();
                        //2.2 合法性校验.
                        if (StringUtils.isEmpty(value) || !CronExpression.isValidExpression(value)) {
                            // omitted code ..
                        }
                        //2.3 返回执行周期(Date)
                        return new CronTrigger(value).nextExecutionTime(triggerContext);*/
                      return null;
                    }
                }
        );
    }

}
