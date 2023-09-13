package com.example.whiteLabelErrorPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller"})
public class WhiteLabelErrorPageApplication {


	public static void main(String[] args) {
		SpringApplication.run(WhiteLabelErrorPageApplication.class, args);
		System.out.println ("Hello");
	}

}
