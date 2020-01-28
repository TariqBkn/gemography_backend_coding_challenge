package com.gitrepos.trends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(value = {"com.gitrepos.trends","com.gitrepos.trends.modelImpl","com.gitrepos.trends.modelImpl"})
public class TrendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendsApplication.class, args);
	}

}
