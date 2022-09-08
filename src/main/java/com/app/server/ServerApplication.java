package com.app.server;

import com.app.server.property.FileStorageProperties;
import com.app.server.property.RealmProperties;
import com.app.server.services.UtilityService;
import com.app.server.services.security.KeycloakService;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class,
        KeycloakSpringBootProperties.class,
        RealmProperties.class
})
public class ServerApplication {



    @Autowired
    UtilityService utilityService;

    Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("Lets get started");

    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
   utilityService.populateDataBase();


        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }




}
