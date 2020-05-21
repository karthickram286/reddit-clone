package com.karthickram.redditclone;

import com.karthickram.redditclone.models.Comment;
import com.karthickram.redditclone.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedditcloneApplication {

	private static final Logger log = LoggerFactory.getLogger(RedditcloneApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedditcloneApplication.class, args);
		System.out.println("Welcome to Reddit clone application!");
	}
}
