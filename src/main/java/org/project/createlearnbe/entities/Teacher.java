package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.project.createlearnbe.constant.Gender;

import java.util.List;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 100)
  private String firstName;

  @Column(nullable = false, length = 100)
  private String lastName;

  @Lob
  @Column(columnDefinition = "TEXT") // For MySQL/Postgres;
  private String introduction;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Gender gender;

  @Column(length = 500)
  private String profileImageUrl;

  @OneToMany(mappedBy = "teacher")
  private List<Clazz> clazzes;
}
