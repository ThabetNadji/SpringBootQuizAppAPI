package com.QuizApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.QuizApp"})
public class QuizAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
		System.out.println("its working ...");
	}
}