package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "clazz")
@Data
public class Clazz {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String brief;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String description;

  private String image;
  private String requirement;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String guarantee;

  @ManyToMany
  @JoinTable(
      name = "class_subject",
      joinColumns = @JoinColumn(name = "class_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id"))
  private List<Subject> subjects;

  @ManyToMany
  @JoinTable(
      name = "class_grade",
      joinColumns = @JoinColumn(name = "class_id"),
      inverseJoinColumns = @JoinColumn(name = "grade_id"))
  private List<Grade> grades;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  private Boolean isDisplayed = true;
  private BigDecimal price = BigDecimal.ZERO;
}
