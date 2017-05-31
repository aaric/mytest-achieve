package com.incarcloud.rooster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring Boot Launcher.
 *
 * @author Aaric, created on 2017-05-27T10:19.
 * @since 1.0-SNAPSHOT
 */
@SpringBootApplication
@ImportResource(locations = "spring-hbase.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
