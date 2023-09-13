package com.example.springbootLearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootLearningApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(SpringbootLearningApplication.class, args);
		Alien a=context.getBean ( Alien.class );
		a.show ();
		Alien a2=context.getBean ( Alien.class );
		a2.show ();
	}

}
