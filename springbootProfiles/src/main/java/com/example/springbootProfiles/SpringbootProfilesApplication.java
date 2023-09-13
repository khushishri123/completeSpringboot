package com.example.springbootProfiles;

import com.example.springbootProfiles.config.EnvBasedConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringbootProfilesApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EnvBasedConfig envBasedConfig;
	public static void main(String[] args) {

		SpringApplication.run(SpringbootProfilesApplication.class, args);
	}

	@Override
	public	void run(String... args)
	{
		System.out.println ("Datasource: "+dataSource);
		envBasedConfig.setup ();
	}


}
