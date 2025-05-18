package com.nghex.exe202;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Exe202Application {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		SpringApplication.run(Exe202Application.class, args);
	}

}
