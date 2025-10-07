package org.project.createlearnbe.dto.response;

import lombok.Data;
import org.project.createlearnbe.constant.RegistrationStatus;

@Data
public class RegistrationResponse {
  private Long id;
  private String customerName;
  private String customerEmail;
  private String phoneNumber;
  private RegistrationStatus status;
}
