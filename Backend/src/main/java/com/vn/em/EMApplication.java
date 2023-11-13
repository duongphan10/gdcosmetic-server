package com.vn.em;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class EMApplication {

    public static void main(String[] args) {
        Environment env = SpringApplication.run(EMApplication.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name");
        if (appName != null) {
            appName = appName.toUpperCase();
        }
        String port = env.getProperty("server.port");
        log.info(" Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info(" ----- START SUCCESS " + appName+ " Application -----");
    }

}
