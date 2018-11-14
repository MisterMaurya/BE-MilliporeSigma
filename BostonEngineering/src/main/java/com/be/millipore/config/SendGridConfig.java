package com.be.millipore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sendgrid.SendGrid;

@Configuration
@PropertySource("classpath:application.properties")
public class SendGridConfig {

	@Value("${spring.sendgrid.api-key}")
	String sendGridAPIKey;

	@Bean
	public SendGrid sendGridAPIKey() {
		return new SendGrid(sendGridAPIKey);
	}

}