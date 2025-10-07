package org.project.createlearnbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.createlearnbe.constant.RegistrationStatus;

@Data
public class ChangeStatusRegistrationRequest {
  @NotNull(message = "Status must not be null")
  private RegistrationStatus status;
}
