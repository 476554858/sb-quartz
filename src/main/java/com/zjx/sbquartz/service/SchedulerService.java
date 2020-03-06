package com.zjx.sbquartz.service;

public interface SchedulerService {
     /**
      * 新增任务
      * @param cron
      * @return
      */
     boolean addCronJob(String cron);

     /**
      * 修改任务
      * @param cron
      * @return
      */
     boolean updateJob(String cron);

     /**
      * 暂停任务
      * @return
      */
     boolean pauseJob();

     /**
      * 恢复任务
      * @return
      */
     boolean resumeJob();

     /**
      * 删除任务
      * @return
      */
     boolean deleteJob();

     /**
      * 定时任务是否存在
      * @return
      */
     boolean isExist();
}
