package com.foxminded.andreimarkov.warehouse;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class WarehouseWebApp {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseWebApp.class, args);
	}

}
