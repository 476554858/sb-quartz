package com.zjx.sbquartz.service;

import com.zjx.sbquartz.entity.CronEntity;
import com.zjx.sbquartz.mapper.CronEntityMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelloJob implements Job{

    @Autowired
    CronEntityMapper cronEntityMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("==========模拟定时任务=============="+sdf.format(new Date()));
        CronEntity cronEntity = new CronEntity();
        cronEntity.setValue("* * * * * ?");
        Map<String,String> map = new HashMap<String, String>();
        map.put("","");
        cronEntityMapper.insert(cronEntity);

    }


   /* public void setCronEntityMapper(CronEntityMapper cronEntityMapper) {
        this.cronEntityMapper = cronEntityMapper;
    }*/
}
