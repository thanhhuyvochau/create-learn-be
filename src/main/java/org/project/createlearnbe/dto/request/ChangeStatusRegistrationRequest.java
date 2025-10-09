package org.project.createlearnbe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
public class ChangeStatusRegistrationRequest {
  @NotNull(message = "Status must not be null")
  private ProcessStatus status;
}
