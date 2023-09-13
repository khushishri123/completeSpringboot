package com.example.springbootLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootLoggerApplication {

	private static Logger LOGGER= LoggerFactory.getLogger(SpringbootLoggerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoggerApplication.class, args);
		LOGGER.info ( "Springboot logger demo" );
		LOGGER.warn("This is a warning Logger");
		LOGGER.error ( "This is a error Logger" );
		LOGGER.debug ( "This is a debug LOGGER" );



	}

	@RequestMapping("/")
	public String welcome()
	{
		return "Hello, Google";
	}
}
