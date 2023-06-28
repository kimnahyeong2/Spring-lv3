package com.sparta.springlv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringLv3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringLv3Application.class, args);
	}

}
