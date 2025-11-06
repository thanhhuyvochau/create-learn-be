package org.project.createlearnbe.dto.response;

import java.time.Instant;
import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
public class RegistrationResponse {
  private Long id;
  private String customerName;
  private String customerEmail;
  private String phoneNumber;
  private ProcessStatus status;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;
  private ClassResponse classResponse;
}
