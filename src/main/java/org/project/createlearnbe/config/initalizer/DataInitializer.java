package org.project.createlearnbe.config.initalizer;

import org.project.createlearnbe.config.AppProperties;
import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.constant.Gender;
import org.project.createlearnbe.entities.*;
import org.project.createlearnbe.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializer {
  private final AppProperties appProperties;

  public DataInitializer(AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  @Bean
  CommandLineRunner initData(
      AccountRepository accountRepository,
      SubjectRepository subjectRepository,
      GradeRepository gradeRepository,
      ConsultationRepository consultationRepository,
      TeacherRepository teacherRepository,
      NewsRepository newsRepository,
      PasswordEncoder passwordEncoder,
      ClazzRepository classRepository) {
    return args -> {
      if (appProperties.isInitMock()) {
        initAdmin(accountRepository, passwordEncoder);
        initSubjects(subjectRepository);
        initGrades(gradeRepository);
        initConsultations(consultationRepository);
        initTeachers(teacherRepository);
        initNews(newsRepository);
        initClazzes(classRepository, subjectRepository, gradeRepository, teacherRepository);
      }
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
      admin.setPassword(passwordEncoder.encode("admin123"));
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
      List<Subject> subjects =
          List.of(
              createSubject(
                  "Scratch Coding",
                  "Learn coding by creating fun games and animations with Scratch.",
                  "/static/icon/scratch.webp"),
              createSubject(
                  "Artificial Intelligence",
                  "Explore how AI works through projects and simulations.",
                  "/static/icon/ai.webp"),
              createSubject(
                  "Minecraft Coding",
                  "Code mods and automate gameplay in Minecraft.",
                  "/static/icon/minecraft.webp"),
              createSubject(
                  "Python",
                  "Learn Python through games, math, and automation projects.",
                  "/static/icon/python.webp"),
              createSubject(
                  "Roblox Coding",
                  "Create Roblox games and worlds using Lua.",
                  "/static/icon/roblox.webp"),
              createSubject(
                  "Robotics",
                  "Build and program robots that move and respond.",
                  "/static/icon/robotics.webp"),
              createSubject(
                  "Mobile Games & Apps",
                  "Design and build mobile applications.",
                  "/static/icon/mobile_apps.webp"),
              createSubject(
                  "Game Development",
                  "Create games using professional tools and engines.",
                  "/static/icon/game_dev.webp"),
              createSubject(
                  "Digital Design",
                  "Master the art of digital creativity and visual storytelling.",
                  "/static/icon/digital_design.webp"),
              createSubject(
                  "AP CS Exams",
                  "Prepare for AP Computer Science A and Principles exams.",
                  "/static/icon/java.webp"),
              createSubject(
                  "Data Science",
                  "Learn how to analyze and visualize data with code.",
                  "/static/icon/data_science.webp"),
              createSubject(
                  "Web Development",
                  "Build modern, responsive websites.",
                  "/static/icon/web_dev.webp"));

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
    subject.setIconBase64(encodeImageToBase64(iconUrl));
    return subject;
  }

  private void initGrades(GradeRepository gradeRepository) {
    if (gradeRepository.count() == 0) {
      List<Grade> grades =
          List.of(
              createGrade(
                  "Grade 1",
                  "Basic introduction to reading, writing, and numbers.",
                  "/static/icon/grade1.png"),
              createGrade(
                  "Grade 2",
                  "Elementary concepts in math, language, and environment studies.",
                  "/static/icon/grade2.png"),
              createGrade(
                  "Grade 3",
                  "Building foundation in science, social studies, and mathematics.",
                  "/static/icon/grade3.png"),
              createGrade(
                  "Grade 4",
                  "Expanding knowledge in history, geography, and applied science.",
                  "/static/icon/grade4.png"),
              createGrade(
                  "Grade 5",
                  "Preparing for middle school with advanced language and math.",
                  "/static/icon/grade5.png"));

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
    grade.setIconBase64(encodeImageToBase64(iconUrl));
    return grade;
  }

  private void initConsultations(ConsultationRepository consultationRepository) {
    if (consultationRepository.count() == 0) {
      List<Consultation> consultations =
          List.of(
              createConsultation("Alice Johnson", "1234567890", "alice@example.com"),
              createConsultation("Bob Smith", "0987654321", "bob@example.com"),
              createConsultation("Charlie Brown", "1122334455", "charlie@example.com"),
              createConsultation("Diana Prince", "2233445566", "diana@example.com"));

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
    if (teacherRepository.count() == 0) {
      List<Teacher> teachers =
          List.of(
              createTeacher(
                  "John",
                  "Doe",
                  "Experienced math teacher with 10+ years of teaching high school students.",
                  Gender.MALE,
                  "/images/john.png"),
              createTeacher(
                  "Jane",
                  "Smith",
                  "Physics teacher passionate about experiments and real-world applications.",
                  Gender.FEMALE,
                  "/images/jane.png"),
              createTeacher(
                  "Michael",
                  "Brown",
                  "Chemistry teacher specializing in organic and inorganic chemistry.",
                  Gender.MALE,
                  "/images/michael.png"),
              createTeacher(
                  "Emily",
                  "Davis",
                  "Biology teacher focused on genetics and environmental sciences.",
                  Gender.FEMALE,
                  "/images/emily.png"));

      teacherRepository.saveAll(teachers);
      System.out.println("Inserted default teachers into database.");
    } else {
      System.out.println("Teachers already initialized, skipping.");
    }
  }

  private Teacher createTeacher(
      String firstName,
      String lastName,
      String introduction,
      Gender gender,
      String profileImageUrl) {
    Teacher teacher = new Teacher();
    teacher.setFirstName(firstName);
    teacher.setLastName(lastName);
    teacher.setIntroduction(introduction);
    teacher.setGender(gender);
    teacher.setProfileImageUrl(profileImageUrl);
    return teacher;
  }

  private void initNews(NewsRepository newsRepository) {
    if (newsRepository.count() == 0) {
      List<News> newsList =
          List.of(
              createNews(
                  "New Semester Announcement",
                  "The new semester starts next month.",
                  "<p>We are excited to announce that the new semester will begin on <b>October 1st</b>. "
                      + "Please make sure to complete your registrations in time. More updates will follow soon.</p>",
                  true),
              createNews(
                  "Science Fair 2025",
                  "Annual Science Fair details announced.",
                  "<h2>Science Fair 2025</h2><p>Our annual science fair will be held on <i>November 15th</i>. "
                      + "Students are encouraged to participate with innovative projects. Prizes will be awarded for the best exhibits.</p>",
                  true),
              createNews(
                  "Library Renovation",
                  "Library will be closed for renovation.",
                  "<p>The school library will be closed for renovation from <b>September 25th to October 10th</b>. "
                      + "We apologize for the inconvenience and promise an improved learning space once it reopens.</p>",
                  false));

      newsRepository.saveAll(newsList);
      System.out.println("Inserted default news into database.");
    } else {
      System.out.println("News already initialized, skipping.");
    }
  }

  private News createNews(String title, String brief, String content, Boolean isDisplay) {
    News news = new News();
    news.setTitle(title);
    news.setBrief(brief);
    news.setContent(content);
    news.setIsDisplay(isDisplay);
    return news;
  }

  private void initClazzes(
      ClazzRepository clazzRepository,
      SubjectRepository subjectRepository,
      GradeRepository gradeRepository,
      TeacherRepository teacherRepository) {
    if (clazzRepository.count() == 0) {
      // pick existing teacher
      Teacher teacher = teacherRepository.findAll().stream().findFirst().orElse(null);

      List<Subject> subjects =
          subjectRepository.findAll().subList(0, Math.min(2, (int) subjectRepository.count()));
      List<Grade> grades =
          gradeRepository.findAll().subList(0, Math.min(2, (int) gradeRepository.count()));

      Clazz mathClass = new Clazz();
      mathClass.setName("Mathematics Excellence");
      mathClass.setBrief("Advanced problem-solving and algebra focus.");
      mathClass.setDescription(
          "This class helps students master mathematics with a focus on algebra, geometry, and calculus.");
      mathClass.setImage("/images/math-class.png");
      mathClass.setRequirement("Basic understanding of arithmetic operations.");
      mathClass.setGuarantee("Students will improve problem-solving skills.");
      mathClass.setSubjects(subjects);
      mathClass.setGrades(grades);
      mathClass.setTeacher(teacher);
      mathClass.setPrice(BigDecimal.valueOf(2000000));
      Clazz physicsClass = new Clazz();
      physicsClass.setName("Physics Exploration");
      physicsClass.setBrief("Understand the world of motion and forces.");
      physicsClass.setDescription(
          "Covers classical mechanics, motion, forces, and introduction to energy.");
      physicsClass.setImage("/images/physics-class.png");
      physicsClass.setRequirement("Interest in science and curiosity about natural phenomena.");
      physicsClass.setGuarantee("Students will build a solid foundation in physics.");
      physicsClass.setSubjects(subjects);
      physicsClass.setGrades(grades);
      physicsClass.setTeacher(teacher);
      physicsClass.setPrice(BigDecimal.ZERO);
      clazzRepository.saveAll(List.of(mathClass, physicsClass));
      System.out.println("Inserted default classes into database.");
    } else {
      System.out.println("Classes already initialized, skipping.");
    }
  }

  private String encodeImageToBase64(String resourcePath) {
    try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
      if (is == null) {
        System.err.println("Image not found: " + resourcePath);
        return null;
      }
      byte[] bytes = is.readAllBytes();
      return Base64.getEncoder().encodeToString(bytes);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load image: " + resourcePath, e);
    }
  }
}
