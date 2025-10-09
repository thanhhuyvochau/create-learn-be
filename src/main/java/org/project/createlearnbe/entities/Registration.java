package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Entity
@Table(name = "registrations")
@Data
public class Registration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerName;
  private String customerEmail;
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private ProcessStatus status;
}
