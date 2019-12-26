package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableScheduling
public class EmailServiceApplication {

	public static void main(String[] args) throws SchedulingException, IOException, ParseException {
		SpringApplication.run(EmailServiceApplication.class, args);
	}
}
