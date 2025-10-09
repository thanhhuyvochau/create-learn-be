package org.project.createlearnbe.dto.response;

import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
public class RegistrationResponse {
  private Long id;
  private String customerName;
  private String customerEmail;
  private String phoneNumber;
  private ProcessStatus status;
}
