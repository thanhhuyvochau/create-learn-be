package org.project.createlearnbe.config.initalizer;

import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.constant.Gender;
import org.project.createlearnbe.entities.*;
import org.project.createlearnbe.repositories.*;
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
                               GradeRepository gradeRepository,
                               ConsultationRepository consultationRepository,
                               TeacherRepository teacherRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            initAdmin(accountRepository, passwordEncoder);
            initSubjects(subjectRepository);
            initGrades(gradeRepository);
            initConsultations(consultationRepository);
            initTeachers(teacherRepository);
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

    private void initGrades(GradeRepository gradeRepository) {
        if (gradeRepository.count() == 0) {
            List<Grade> grades = List.of(
                    createGrade("Grade 1", "Basic introduction to reading, writing, and numbers.", "https://example.com/icons/grade1.png"),
                    createGrade("Grade 2", "Elementary concepts in math, language, and environment studies.", "https://example.com/icons/grade2.png"),
                    createGrade("Grade 3", "Building foundation in science, social studies, and mathematics.", "https://example.com/icons/grade3.png"),
                    createGrade("Grade 4", "Expanding knowledge in history, geography, and applied science.", "https://example.com/icons/grade4.png"),
                    createGrade("Grade 5", "Preparing for middle school with advanced language and math.", "https://example.com/icons/grade5.png")
            );

            gradeRepository.saveAll(grades);
            System.out.println("Inserted default grades into database.");
        } else {
            System.out.println("Grades already initialized, skipping.");
        }
    }

    private Grade createGrade(String name, String description, String iconUrl) {
        Grade grade = new Grade();
        grade.setName(name);
        grade.setDescription(description);
        grade.setIconUrl(iconUrl);
        return grade;
    }

    private void initConsultations(ConsultationRepository consultationRepository) {
        if (consultationRepository.count() == 0) {
            List<Consultation> consultations = List.of(
                    createConsultation("Alice Johnson", "1234567890", "alice@example.com"),
                    createConsultation("Bob Smith", "0987654321", "bob@example.com"),
                    createConsultation("Charlie Brown", "1122334455", "charlie@example.com"),
                    createConsultation("Diana Prince", "2233445566", "diana@example.com")
            );

            consultationRepository.saveAll(consultations);
            System.out.println("Inserted default consultations into database.");
        } else {
            System.out.println("Consultations already initialized, skipping.");
        }
    }

    private Consultation createConsultation(String customerName, String phone, String email) {
        Consultation consultation = new Consultation();
        consultation.setCustomerName(customerName);
        consultation.setPhoneNumber(phone);
        consultation.setEmail(email);
        return consultation;
    }

    private void initTeachers(TeacherRepository teacherRepository) {
        if (teacherRepository.count() == 1) {
            List<Teacher> teachers = List.of(
                    createTeacher("John", "Doe", "Experienced math teacher with 10+ years of teaching high school students.", Gender.MALE, "https://example.com/images/john.png"),
                    createTeacher("Jane", "Smith", "Physics teacher passionate about experiments and real-world applications.", Gender.FEMALE, "https://example.com/images/jane.png"),
                    createTeacher("Michael", "Brown", "Chemistry teacher specializing in organic and inorganic chemistry.", Gender.MALE, "https://example.com/images/michael.png"),
                    createTeacher("Emily", "Davis", "Biology teacher focused on genetics and environmental sciences.", Gender.FEMALE, "https://example.com/images/emily.png")
            );

            teacherRepository.saveAll(teachers);
            System.out.println("Inserted default teachers into database.");
        } else {
            System.out.println("Teachers already initialized, skipping.");
        }
    }

    private Teacher createTeacher(String firstName, String lastName, String introduction, Gender gender, String profileImageUrl) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setIntroduction(introduction);
        teacher.setGender(gender);
        teacher.setProfileImageUrl(profileImageUrl);
        return teacher;
    }
}
