package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.createlearnbe.config.audit.Auditable;
import org.project.createlearnbe.constant.ProcessStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "consultation")
public class Consultation extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerName;
  private String phoneNumber;
  private String email;
  private String content;

  @Enumerated(EnumType.STRING)
  private ProcessStatus status;
}
