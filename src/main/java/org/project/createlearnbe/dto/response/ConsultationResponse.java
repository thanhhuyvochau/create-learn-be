package org.project.createlearnbe.dto.response;

import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

import java.time.Instant;

@Data
public class ConsultationResponse {
  private Long id;
  private String customerName;
  private String phoneNumber;
  private String email;
  private String content;
  private ProcessStatus status;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;
}
