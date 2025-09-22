package org.project.createlearnbe.config.initalizer;

import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.entities.Subject;
import org.project.createlearnbe.repositories.AccountRepository;
import org.project.createlearnbe.repositories.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AccountRepository accountRepository,
                               SubjectRepository subjectRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            initAdmin(accountRepository, passwordEncoder);
            initSubjects(subjectRepository);
        };
    }

    private void initAdmin(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
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
            System.out.println("Root admin account created: " + adminUsername);
        } else {
            System.out.println("Root admin already exists: " + adminUsername);
        }
    }

    private void initSubjects(SubjectRepository subjectRepository) {
        if (subjectRepository.count() == 0) {
            List<Subject> subjects = List.of(
                    createSubject("Mathematics", "Fundamental subject covering algebra, geometry, and calculus.", "https://example.com/icons/math.png"),
                    createSubject("Physics", "Study of matter, motion, energy, and the forces of nature.", "https://example.com/icons/physics.png"),
                    createSubject("Chemistry", "Science of substances, their reactions, and properties.", "https://example.com/icons/chemistry.png"),
                    createSubject("Biology", "Study of living organisms, their structure, and life processes.", "https://example.com/icons/biology.png"),
                    createSubject("Computer Science", "Covers programming, algorithms, databases, and software design.", "https://example.com/icons/compsci.png")
            );

            subjectRepository.saveAll(subjects);
            System.out.println("Inserted default subjects into database.");
        } else {
            System.out.println("Subjects already initialized, skipping.");
        }
    }

    private Subject createSubject(String name, String description, String iconUrl) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDescription(description);
        subject.setIconUrl(iconUrl);
        return subject;
    }
}