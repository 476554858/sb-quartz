package com.zjx.sbquartz;

import com.zjx.sbquartz.service.HelloJob;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SbQuartzApplicationTests {

	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	@Test
	void contextLoads() throws Exception {
		String jobName = "job";
		String jobGroup = "group";
		//springboot中是schedulerFactoryBean
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail != null) {
			System.out.println("job已存在");
		} else {
			//创建job信息
			jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity(jobName, jobGroup).build();
			//用jopData来传递数据
			jobDetail.getJobDataMap().put(jobName, HelloJob.class.getName());

			//表达式调度构建器
			String cron = "*/2 * * * * ? *";
			//校验cron表达式
			CronExpression.isValidExpression(cron);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

			//按新的cronException表达式构建一个新的triger
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(jobName + "_trigger", jobGroup + "_trigger").withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		}

		TimeUnit.SECONDS.sleep(30);
	}

}
