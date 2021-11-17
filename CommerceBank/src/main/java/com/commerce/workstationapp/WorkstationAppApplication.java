package com.commerce.workstationapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.UserRepo;
import com.commerce.workstationapp.service.UserService;
@EnableAutoConfiguration
@SpringBootApplication
public class WorkstationAppApplication {
	

    public static void main(String[] args) {
        SpringApplication.run(WorkstationAppApplication.class, args);       
    
    }



}
