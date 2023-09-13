package com.example.springbootLoggerWithLogback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootLoggerWithLogbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoggerWithLogbackApplication.class, args);
	}

	@RequestMapping("/")
	public String welcome(){
		String name="kk";
		if(name.length ()==2)
		{
			throw new RuntimeException ("Name length is 2");
		}
		return "Hello "+name;
	}
}
