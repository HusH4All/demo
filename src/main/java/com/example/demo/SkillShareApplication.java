package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class SkillShareApplication {

	private static final Logger log = Logger.getLogger(SkillShareApplication.class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(SkillShareApplication.class).run(args);
	}
}
