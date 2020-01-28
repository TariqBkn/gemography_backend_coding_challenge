package com.gitrepos.trends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.gitrepos.trends","com.gitrepos.trends.modelImpl","com.gitrepos.trends.modelImpl"})
// disabling the auto configuration for the a datasource because no DB is used
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class TrendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendsApplication.class, args);
	}

}
