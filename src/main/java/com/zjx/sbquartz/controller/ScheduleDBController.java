package com.zjx.sbquartz.controller;

import com.zjx.sbquartz.entity.CronEntity;
import com.zjx.sbquartz.mapper.CronEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
@RequestMapping("/db")
public class ScheduleDBController {

    @Autowired
    CronEntityMapper cronEntityMapper;

    @RequestMapping("/updateCron")
    public boolean testDBSchedule(@RequestParam String cron){
        CronEntity cronEntity = null;
        try {
            cronEntity = new CronEntity();
            cronEntity.setId(1);
            cronEntity.setValue(cron);
            cronEntityMapper.updateById(cronEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
