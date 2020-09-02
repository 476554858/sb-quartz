package com.zjx.sbquartz.config;

import net.sf.jabb.quartz.AutowiringSpringBeanJobFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@EnableScheduling
@Configuration
public class QuartzConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext){
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

//    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory){
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);

        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);

        return schedulerFactoryBean;
    }

    @Bean(name = "schedulerFactoryBean2")
    public SchedulerFactoryBean schedulerFactoryBean2(JobFactory jobFactory) throws IOException {
        //获取配置属性
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz_db.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties pro = propertiesFactoryBean.getObject();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setQuartzProperties(pro);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        return factory;
    }
}
