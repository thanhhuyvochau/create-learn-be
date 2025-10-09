package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
@Entity
@Table(name = "consultation")
public class Consultation {
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
