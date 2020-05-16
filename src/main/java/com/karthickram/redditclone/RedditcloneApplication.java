package com.karthickram.redditclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class RedditcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditcloneApplication.class, args);
		System.out.println("Welcome to Reddit clone application!");
	}

	@Bean
	@Profile("dev")
	CommandLineRunner runner() {
		return args -> {
			System.out.println("Only visible in dev environment");
		};
	}
}
