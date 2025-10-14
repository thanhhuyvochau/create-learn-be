package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.project.createlearnbe.config.audit.Auditable;
import org.project.createlearnbe.constant.ProcessStatus;

@Entity
@Table(name = "registrations")
@Getter
@Setter
public class Registration extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerName;
  private String customerEmail;
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private ProcessStatus status;


}
