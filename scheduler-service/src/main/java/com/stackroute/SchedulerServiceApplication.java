package com.stackroute;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableScheduling
public class SchedulerServiceApplication {


	public static void main(String[] args) throws SchedulerException, ParseException, IOException {

		SpringApplication.run(SchedulerServiceApplication.class,args);

	}

}
