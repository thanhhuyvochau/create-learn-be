package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_benefit")
@Data
public class JobBenefit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_posting_id", nullable = false)
  private JobPosting jobPosting;

  private String icon;

  private String title;

  @Column(columnDefinition = "TEXT")
  private String body;

  @Column(name = "display_order")
  private Integer displayOrder;
}
