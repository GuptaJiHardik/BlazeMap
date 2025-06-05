package com.blaze.map;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlazeMapApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("KAFKA_BOOTSTRAP", dotenv.get("KAFKA_BOOTSTRAP"));
		System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
		System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));

		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
		System.setProperty("TWITTER_BEARER_TOKEN", dotenv.get("TWITTER_BEARER_TOKEN"));
		System.setProperty("NEWS_API_KEY", dotenv.get("NEWS_API_KEY"));
//		System.setProperty("REDDIT_CLIENT_ID", dotenv.get("REDDIT_CLIENT_ID"));
//		System.setProperty("REDDIT_CLIENT_SECRET", dotenv.get("REDDIT_CLIENT_SECRET"));
//		System.setProperty("REDDIT_USERNAME", dotenv.get("REDDIT_USERNAME"));
//		System.setProperty("REDDIT_PASSWORD", dotenv.get("REDDIT_PASSWORD"));

		SpringApplication.run(BlazeMapApplication.class, args);

	}

}
