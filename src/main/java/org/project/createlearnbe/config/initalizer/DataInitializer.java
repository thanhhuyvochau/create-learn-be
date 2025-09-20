package org.project.createlearnbe.config.initalizer;

import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initAdmin(AccountRepository accountRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "root@system.com";
            String adminUsername = "root";

            Optional<Account> existingAdmin = accountRepository.findByUsername(adminUsername);

            if (existingAdmin.isEmpty()) {
                Account admin = new Account();
                admin.setEmail(adminEmail);
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode("admin123")); // default password
                admin.setRole(Role.ADMIN);
                admin.setPhone("0000000000");

                accountRepository.save(admin);
                System.out.println(admin.getId().toString());
                System.out.println("Root admin account created: " + adminUsername);
            } else {
                System.out.println("Root admin already exists: " + adminUsername);
            }
        };
    }
}