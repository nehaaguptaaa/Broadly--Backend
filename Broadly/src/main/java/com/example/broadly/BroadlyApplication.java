package com.example.broadly;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BroadlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BroadlyApplication.class, args);
	}

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
