package com.example.friendmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@ComponentScan("com.example")
//@EnableJpaRepositories(basePackages = "com.example", repositoryBaseClass = FriendRepository.class)
//@EnableR2dbcRepositories
public class FriendManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendManagementApplication.class, args);
	}

}
