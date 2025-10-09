package org.project.createlearnbe.dto.request;

import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
public class RegistrationRequest {
  private String customerName;
  private String customerEmail;
  private String phoneNumber;
  private ProcessStatus status;
}
