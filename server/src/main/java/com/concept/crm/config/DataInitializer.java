package com.concept.crm.config;

import com.concept.crm.entity.User;
import com.concept.crm.enums.UserRole;
import com.concept.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@crm.com").isEmpty()) {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("CRM")
                    .email("admin@crm.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(admin);
            log.info("Usuario admin creado -> email: admin@crm.com  password: admin123");
        }
    }
}
