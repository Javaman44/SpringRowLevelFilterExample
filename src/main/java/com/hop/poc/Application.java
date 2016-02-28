package com.hop.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = 
    {"com.hop.data.dynamicFilter",
     "com.hop.poc.config",
     "com.hop.poc.entity",
     "com.hop.poc.service",
     "com.hop.poc.business",
    })
public class Application {	
    public static void main(String[] args) throws Exception {
            SpringApplication.run(Application.class, args);
    }
}
