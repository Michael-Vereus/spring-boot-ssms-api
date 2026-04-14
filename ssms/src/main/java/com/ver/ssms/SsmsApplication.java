package com.ver.ssms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsmsApplication.class, args);
		System.out.println("Server is Running !");
	}

}
