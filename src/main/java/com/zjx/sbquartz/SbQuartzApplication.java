package com.zjx.sbquartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.zjx.sbquartz.mapper")
@SpringBootApplication
public class SbQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbQuartzApplication.class, args);
	}

}
