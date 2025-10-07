package org.project.createlearnbe.dto.request;

import lombok.Data;
import org.project.createlearnbe.constant.RegistrationStatus;

@Data
public class RegistrationRequest {
  private String customerName;
  private String customerEmail;
  private String phoneNumber;
  private RegistrationStatus status;
}
