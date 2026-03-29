package org.project.createlearnbe.config.initalizer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.project.createlearnbe.config.AppProperties;
import org.project.createlearnbe.constant.BadgeVariant;
import org.project.createlearnbe.constant.Gender;
import org.project.createlearnbe.constant.JobType;
import org.project.createlearnbe.constant.ProcessStatus;
import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.entities.*;
import org.project.createlearnbe.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
      ClazzRepository classRepository,
      ScheduleRepository scheduleRepository,
      JobPostingRepository jobPostingRepository) {
    return args -> {
      initAdmin(accountRepository, passwordEncoder); // Always ensure admin exists
      if (appProperties.isInitMock()) {
        initSubjects(subjectRepository);
        initGrades(gradeRepository);
        initConsultations(consultationRepository);
        initTeachers(teacherRepository);
        initNews(newsRepository);
        initClazzes(
            classRepository,
            subjectRepository,
            gradeRepository,
            teacherRepository,
            scheduleRepository);
        initJobPostings(jobPostingRepository);
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
      admin.setActivated(true);
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
    consultation.setContent("I want to know more about your classes.");
    consultation.setStatus(ProcessStatus.PROCESSING);
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
                  "/create-learn-storage/teacher.jpg"),
              createTeacher(
                  "Jane",
                  "Smith",
                  "Physics teacher passionate about experiments and real-world applications.",
                  Gender.FEMALE,
                  "/create-learn-storage/teacher.jpg"),
              createTeacher(
                  "Michael",
                  "Brown",
                  "Chemistry teacher specializing in organic and inorganic chemistry.",
                  Gender.MALE,
                  "/create-learn-storage/teacher.jpg"),
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
    news.setImage(
        "/create-learn-storage/954e34da-80fa-4d66-af03-0ce80fd86da1-Coding_planning_school_year_1f33b3289b.webp");
    return news;
  }

  private void initClazzes(
      ClazzRepository clazzRepository,
      SubjectRepository subjectRepository,
      GradeRepository gradeRepository,
      TeacherRepository teacherRepository,
      ScheduleRepository scheduleRepository) {

    if (clazzRepository.count() == 0) {
      Teacher teacher = teacherRepository.findAll().stream().findFirst().orElse(null);

      List<Subject> subjects =
          subjectRepository.findAll().subList(0, Math.min(2, (int) subjectRepository.count()));
      List<Grade> grades_1 =
          gradeRepository.findAll().subList(0, Math.min(2, (int) gradeRepository.count()));
      List<Grade> grades_2 =
          gradeRepository.findAll().subList(1, Math.min(3, (int) gradeRepository.count()));
      Clazz mathClass = new Clazz();
      mathClass.setName("Mathematics Excellence");
      mathClass.setBrief("Advanced problem-solving and algebra focus.");
      mathClass.setDescription(
          "This class helps students master mathematics with a focus on algebra, geometry, and calculus.");
      mathClass.setImage("/create-learn-storage/math.png");
      mathClass.setRequirement("Basic understanding of arithmetic operations.");
      mathClass.setGuarantee("Students will improve problem-solving skills.");
      mathClass.setSubjects(subjects);
      mathClass.setGrades(grades_1);
      mathClass.setTeacher(teacher);
      mathClass.setPrice(BigDecimal.valueOf(2000000));

      Clazz physicsClass = new Clazz();
      physicsClass.setName("Physics Exploration");
      physicsClass.setBrief("Understand the world of motion and forces.");
      physicsClass.setDescription(
          "Covers classical mechanics, motion, forces, and introduction to energy.");
      physicsClass.setImage("/create-learn-storage/physic.jpg");
      physicsClass.setRequirement("Interest in science and curiosity about natural phenomena.");
      physicsClass.setGuarantee("Students will build a solid foundation in physics.");
      physicsClass.setSubjects(subjects);
      physicsClass.setGrades(grades_2);
      physicsClass.setTeacher(teacher);
      physicsClass.setPrice(BigDecimal.ZERO);

      clazzRepository.saveAll(List.of(mathClass, physicsClass));

      // --- Add Schedules ---
      List<Schedule> schedules =
          List.of(
              createSchedule("Monday - 8am to 9am", mathClass),
              createSchedule("Wednesday - 10am to 11am", mathClass),
              createSchedule("Tuesday - 8am to 9am", physicsClass),
              createSchedule("Thursday - 10am to 11am", physicsClass));

      scheduleRepository.saveAll(schedules);

      System.out.println("Inserted default classes and schedules into database.");
    } else {
      System.out.println("Classes already initialized, skipping.");
    }
  }

  private Schedule createSchedule(String time, Clazz clazz) {
    Schedule schedule = new Schedule();
    schedule.setTime(time);
    schedule.setClazz(clazz);
    return schedule;
  }

  private void initJobPostings(JobPostingRepository jobPostingRepository) {
    if (jobPostingRepository.count() == 0) {
      List<JobPosting> postings = new ArrayList<>();

      // Job 1 — Senior Algorithmic Instructor
      JobPosting job1 = new JobPosting();
      job1.setTitle("Senior Algorithmic Instructor");
      job1.setDepartment("Mathematics");
      job1.setLocation("Cambridge (Hybrid)");
      job1.setBadgeVariant(BadgeVariant.SECONDARY);
      job1.setType(JobType.FULL_TIME);
      job1.setDescription(List.of(
          "AlgoCore Education đang tìm kiếm một Giảng Viên Thuật Toán Cấp Cao xuất sắc để gia nhập đội ngũ giảng viên học thuật tinh hoa của chúng tôi. Vị trí này dành cho những nhà giáo dục có tầm nhìn, với niềm đam mê sâu sắc về lý thuyết toán học và năng lực sư phạm để truyền cảm hứng cho thế hệ các nhà tư duy thuật toán tiếp theo.",
          "Với tư cách là Giảng Viên Cấp Cao, bạn không chỉ dẫn dắt các buổi giảng dạy cấp cao mà còn đóng góp vào sự phát triển liên tục của chương trình học độc quyền của chúng tôi. Bạn sẽ làm việc tại giao điểm giữa sự nghiêm túc học thuật truyền thống và các ứng dụng tính toán hiện đại."
      ));
      job1.setRequirements(List.of(
          "Bằng Thạc Sĩ hoặc Tiến Sĩ chuyên ngành Toán học, Khoa học Máy tính hoặc lĩnh vực liên quan từ trường đại học được công nhận toàn cầu.",
          "Tối thiểu 7 năm kinh nghiệm giảng dạy, ưu tiên trong môi trường trung học hoặc đại học có kết quả học tập cao.",
          "Thành thạo Python, MATLAB hoặc Mathematica cho mục đích giảng dạy.",
          "Kỹ năng giao tiếp xuất sắc với khả năng chuyển hóa các khái niệm trừu tượng phức tạp thành kết quả học tập dễ tiếp thu."
      ));
      job1.setDeadline("Oct 15, 2025");
      job1.setRecruiter("Academic HR Team");
      job1.setReference("#STEM-ALG-2025");
      addResponsibilities(job1, List.of(
          new String[]{"menu_book", "Xuất Sắc Về Chương Trình Học", "Dẫn dắt việc phát triển và hoàn thiện các học phần Thuật Toán Nâng Cao và Toán Học Rời Rạc."},
          new String[]{"group", "Hướng Dẫn & Cố Vấn", "Cung cấp huấn luyện sư phạm và phát triển chuyên môn cho các giảng viên trẻ."},
          new String[]{"insights", "Theo Dõi Kết Quả Học Tập", "Sử dụng nền tảng phân tích của AlgoCore để theo dõi tiến trình của học viên và điều chỉnh lộ trình học tập."},
          new String[]{"hub", "Phối Hợp Liên Ngành", "Hợp tác với bộ phận Khoa Học Máy Tính để tích hợp các chứng minh toán học vào các dự án lập trình."}
      ));
      addBenefits(job1, List.of(
          new String[]{"payments", "Mức Lương Cạnh Tranh", "Lương ở mức cao so với thị trường kèm theo thưởng học thuật dựa trên hiệu suất."},
          new String[]{"rocket_launch", "Hỗ Trợ Nghiên Cứu", "Ngân sách hàng năm dành riêng cho việc xuất bản học thuật và tham dự hội nghị."},
          new String[]{"favorite", "Chăm Sóc Sức Khỏe Toàn Diện", "Bảo hiểm y tế tư nhân cao cấp và các chương trình sức khỏe tâm thần được trợ cấp hoàn toàn."},
          new String[]{"home", "Môi Trường Linh Hoạt", "Mô hình làm việc kết hợp tập trung vào kết quả thay vì hiện diện vật lý."}
      ));
      postings.add(job1);

      // Job 2 — Full Stack Developer (LMS)
      JobPosting job2 = new JobPosting();
      job2.setTitle("Full Stack Developer (LMS)");
      job2.setDepartment("Coding");
      job2.setLocation("Remote / London");
      job2.setBadgeVariant(BadgeVariant.PRIMARY);
      job2.setType(JobType.FULL_TIME);
      job2.setDescription(List.of(
          "Chúng tôi đang tìm kiếm một Lập Trình Viên Full Stack có kỹ năng để giúp xây dựng và duy trì Hệ Thống Quản Lý Học Tập (LMS). Bạn sẽ làm việc chặt chẽ với các nhà giáo dục và nhà thiết kế sản phẩm để cung cấp trải nghiệm học tập số liền mạch."
      ));
      job2.setRequirements(List.of(
          "Bằng Cử Nhân chuyên ngành Khoa Học Máy Tính hoặc kinh nghiệm thực tế tương đương.",
          "Thành thạo TypeScript, Angular hoặc React và Node.js.",
          "Kinh nghiệm với cơ sở dữ liệu quan hệ và thiết kế REST API."
      ));
      job2.setDeadline("Nov 1, 2025");
      job2.setRecruiter("Tech Hiring Team");
      job2.setReference("#TECH-FSD-2025");
      addResponsibilities(job2, List.of(
          new String[]{"code", "Phát Triển Tính Năng", "Xây dựng và triển khai các tính năng LMS mới trên toàn bộ hệ thống sử dụng công nghệ web hiện đại."},
          new String[]{"bug_report", "Đảm Bảo Chất Lượng", "Viết các bài kiểm tra đơn vị và tích hợp, đồng thời tham gia vào quy trình đánh giá mã nguồn."}
      ));
      addBenefits(job2, List.of(
          new String[]{"payments", "Mức Lương Cạnh Tranh", "Lương theo thị trường với đánh giá hiệu suất hàng năm."},
          new String[]{"home", "Ưu Tiên Làm Từ Xa", "Làm việc từ bất cứ đâu với giờ làm việc linh hoạt."}
      ));
      postings.add(job2);

      // Job 3 — Student Success Lead
      JobPosting job3 = new JobPosting();
      job3.setTitle("Student Success Lead");
      job3.setDepartment("Admissions");
      job3.setLocation("Remote");
      job3.setBadgeVariant(BadgeVariant.TERTIARY);
      job3.setType(JobType.FULL_TIME);
      job3.setDescription(List.of(
          "Trưởng Nhóm Hỗ Trợ Học Viên sẽ là người đồng hành cùng học viên trong toàn bộ hành trình từ lúc nhập học đến khi tốt nghiệp, đảm bảo mỗi người học đều có sự hỗ trợ cần thiết để phát triển."
      ));
      job3.setRequirements(List.of(
          "Kinh nghiệm đã được chứng minh trong lĩnh vực dịch vụ sinh viên, tư vấn học tập hoặc vai trò liên quan.",
          "Khả năng giao tiếp đồng cảm với kỹ năng tổ chức tốt."
      ));
      job3.setDeadline("Oct 30, 2025");
      job3.setRecruiter("People & Culture Team");
      job3.setReference("#ADM-SSL-2025");
      addResponsibilities(job3, List.of(
          new String[]{"support_agent", "Hỗ Trợ Học Viên", "Là đầu mối liên hệ chính cho các vấn đề về phúc lợi và học tập của học viên."},
          new String[]{"insights", "Phân Tích Tỷ Lệ Duy Trì", "Theo dõi các chỉ số tương tác và chủ động liên hệ với những học viên có nguy cơ bỏ học."}
      ));
      addBenefits(job3, List.of(
          new String[]{"favorite", "Gói Phúc Lợi Sức Khỏe", "Phúc lợi sức khỏe và thể chất toàn diện."},
          new String[]{"home", "Hoàn Toàn Từ Xa", "Làm việc từ nơi bạn năng suất nhất."}
      ));
      postings.add(job3);

      // Job 4 — Curriculum Designer (Python)
      JobPosting job4 = new JobPosting();
      job4.setTitle("Curriculum Designer (Python)");
      job4.setDepartment("Coding");
      job4.setLocation("Remote");
      job4.setBadgeVariant(BadgeVariant.PRIMARY);
      job4.setType(JobType.CONTRACT);
      job4.setDescription(List.of(
          "Chúng tôi đang tìm kiếm một Nhà Thiết Kế Chương Trình Học có kinh nghiệm để xây dựng các tài liệu học Python hấp dẫn, phù hợp với chuẩn mực cho học sinh trung học và sau trung học."
      ));
      job4.setRequirements(List.of(
          "Kỹ năng lập trình Python vững chắc và kinh nghiệm giảng dạy hoặc gia sư.",
          "Nền tảng về thiết kế giảng dạy hoặc lĩnh vực giáo dục liên quan được ưu tiên."
      ));
      job4.setDeadline("Dec 1, 2025");
      job4.setRecruiter("Curriculum Team");
      job4.setReference("#CUR-PYD-2025");
      addResponsibilities(job4, List.of(
          new String[]{"menu_book", "Sáng Tạo Nội Dung", "Thiết kế giáo án, bài tập và dự án cho các khóa học Python ở nhiều cấp độ kỹ năng khác nhau."},
          new String[]{"group", "Hợp Tác Với Giảng Viên", "Phối hợp với giảng viên để cải tiến nội dung dựa trên phản hồi của học viên."}
      ));
      addBenefits(job4, List.of(
          new String[]{"rocket_launch", "Tự Do Sáng Tạo", "Quyền tự chủ đáng kể trong việc định hướng và cấu trúc nội dung."},
          new String[]{"payments", "Mức Thù Lao Hợp Đồng Cạnh Tranh", "Mức thù lao theo giờ hoặc theo dự án tương xứng với kinh nghiệm."}
      ));
      postings.add(job4);

      // Job 5 — Academic Research Fellow
      JobPosting job5 = new JobPosting();
      job5.setTitle("Academic Research Fellow");
      job5.setDepartment("Mathematics");
      job5.setLocation("San Francisco");
      job5.setBadgeVariant(BadgeVariant.SECONDARY);
      job5.setType(JobType.FULL_TIME);
      job5.setDescription(List.of(
          "Nghiên Cứu Sinh Học Thuật sẽ đóng góp vào công trình nghiên cứu sư phạm ngày càng phát triển của AlgoCore, khám phá giao điểm giữa giáo dục toán học và tư duy tính toán."
      ));
      job5.setRequirements(List.of(
          "Bằng Tiến Sĩ chuyên ngành Toán học, Giáo dục hoặc lĩnh vực liên quan.",
          "Hồ sơ xuất bản vững chắc trong lĩnh vực nghiên cứu giáo dục toán học hoặc STEM."
      ));
      job5.setDeadline("Jan 15, 2026");
      job5.setRecruiter("Research Office");
      job5.setReference("#RES-ARF-2025");
      addResponsibilities(job5, List.of(
          new String[]{"science", "Nghiên Cứu", "Tiến hành các nghiên cứu độc lập và cộng tác trong lĩnh vực giáo dục toán học."},
          new String[]{"menu_book", "Xuất Bản", "Biên soạn và đồng tác giả các bài báo cho các tạp chí được bình duyệt và hội nghị khoa học."}
      ));
      addBenefits(job5, List.of(
          new String[]{"rocket_launch", "Ngân Sách Nghiên Cứu", "Khoản phân bổ hàng năm hào phóng cho hội nghị và xuất bản."},
          new String[]{"payments", "Học Bổng Nghiên Cứu", "Học bổng cạnh tranh kèm phụ cấp nhà ở cho các nghiên cứu sinh tại San Francisco."}
      ));
      postings.add(job5);

      // Jobs 6–11 — minimal stubs (same department/location as job 5)
      String[][] stubs = {
          {"#RES-ARF-2025-B"},
          {"#RES-ARF-2025-C"},
          {"#RES-ARF-2025-D"},
          {"#RES-ARF-2025-E"},
          {"#RES-ARF-2025-F"},
          {"#RES-ARF-2025-G"},
      };
      for (String[] stub : stubs) {
        JobPosting j = new JobPosting();
        j.setTitle("Academic Research Fellow");
        j.setDepartment("Mathematics");
        j.setLocation("San Francisco");
        j.setBadgeVariant(BadgeVariant.SECONDARY);
        j.setType(JobType.FULL_TIME);
        j.setDescription(List.of("Vị trí nghiên cứu sinh tập trung vào giáo dục toán học thuật toán."));
        j.setDeadline("Jan 15, 2026");
        j.setRecruiter("Research Office");
        j.setReference(stub[0]);
        postings.add(j);
      }

      jobPostingRepository.saveAll(postings);
      System.out.println("Inserted default job postings into database.");
    } else {
      System.out.println("Job postings already initialized, skipping.");
    }
  }

  private void addResponsibilities(JobPosting posting, List<String[]> items) {
    for (int i = 0; i < items.size(); i++) {
      String[] item = items.get(i);
      JobResponsibility r = new JobResponsibility();
      r.setIcon(item[0]);
      r.setTitle(item[1]);
      r.setBody(item[2]);
      r.setDisplayOrder(i);
      r.setJobPosting(posting);
      posting.getResponsibilities().add(r);
    }
  }

  private void addBenefits(JobPosting posting, List<String[]> items) {
    for (int i = 0; i < items.size(); i++) {
      String[] item = items.get(i);
      JobBenefit b = new JobBenefit();
      b.setIcon(item[0]);
      b.setTitle(item[1]);
      b.setBody(item[2]);
      b.setDisplayOrder(i);
      b.setJobPosting(posting);
      posting.getBenefits().add(b);
    }
  }

  private String encodeImageToBase64(String resourcePath) {
    try {
      InputStream is = getClass().getResourceAsStream(resourcePath);
      if (is == null) {
        // Fallback for local file system
        Path path = Paths.get("src/main/resources" + resourcePath);
        if (Files.exists(path)) {
          is = Files.newInputStream(path);
        } else {
          System.err.println("Image not found: " + resourcePath);
          return null;
        }
      }
      byte[] bytes = is.readAllBytes();
      return Base64.getEncoder().encodeToString(bytes);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load image: " + resourcePath, e);
    }
  }
}
