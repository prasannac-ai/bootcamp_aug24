package com.example.redisdemo.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisdemoApplication.class, args);
	}

}
