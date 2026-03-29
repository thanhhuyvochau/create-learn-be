package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.project.createlearnbe.constant.BadgeVariant;
import org.project.createlearnbe.constant.JobType;
import org.project.createlearnbe.utils.StringListConverter;

@Entity
@Table(name = "job_posting")
@Data
public class JobPosting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String department;

  @Column(nullable = false)
  private String location;

  @Enumerated(EnumType.STRING)
  @Column(name = "badge_variant")
  private BadgeVariant badgeVariant;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private JobType type;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  @Convert(converter = StringListConverter.class)
  private List<String> description = new ArrayList<>();

  @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("displayOrder ASC")
  private List<JobResponsibility> responsibilities = new ArrayList<>();

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  @Convert(converter = StringListConverter.class)
  private List<String> requirements = new ArrayList<>();

  @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("displayOrder ASC")
  private List<JobBenefit> benefits = new ArrayList<>();

  @Column(name = "deadline")
  private String deadline;

  private String recruiter;

  private String reference;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "deleted_at")
  private Instant deletedAt;
}
