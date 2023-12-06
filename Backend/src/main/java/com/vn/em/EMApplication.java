package com.vn.em;

import com.vn.em.constant.DataConstant;
import com.vn.em.domain.entity.Role;
import com.vn.em.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class EMApplication {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        Environment env = SpringApplication.run(EMApplication.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name");
        if (appName != null) {
            appName = appName.toUpperCase();
        }
        String port = env.getProperty("server.port");
        log.info(" Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info(" ----- START SUCCESS " + appName + " Application -----");
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            //init role
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(DataConstant.Role.LEADER.getId(), DataConstant.Role.LEADER.getName(), null, null));
                roleRepository.save(new Role(DataConstant.Role.MANAGER.getId(), DataConstant.Role.MANAGER.getName(), null, null));
                roleRepository.save(new Role(DataConstant.Role.EMPLOYEE.getId(), DataConstant.Role.EMPLOYEE.getName(), null, null));
            }
        };
    }

}
