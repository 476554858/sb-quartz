package com.zjx.sbquartz.controller;

import com.zjx.sbquartz.service.SchedulerService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/job")
@Controller
public class SchedulerController {

    @Autowired
    SchedulerService schedulerService;

    @ResponseBody
    @RequestMapping("/check")
    public Boolean cronCheck(@RequestParam String cron){
        return CronExpression.isValidExpression(cron);
    }

    @ResponseBody
    @RequestMapping("/add")
    public Boolean addCronJob(@RequestParam String cron){
        return schedulerService.addCronJob(cron);
    }

    @ResponseBody
    @RequestMapping("/update")
    public Boolean updateCronJob(@RequestParam String cron){
        return schedulerService.updateJob(cron);
    }

    @ResponseBody
    @RequestMapping("/pause")
    public Boolean pauseJob(){
        return schedulerService.pauseJob();
    }

    @ResponseBody
    @RequestMapping("/resume")
    public Boolean resumeJob(){
        return schedulerService.resumeJob();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Boolean deleteJob(){
        return schedulerService.deleteJob();
    }

    @ResponseBody
    @RequestMapping("/isExist")
    public Boolean isExist(){
        return schedulerService.isExist();
    }
}
