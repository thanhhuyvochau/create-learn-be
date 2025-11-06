package org.project.createlearnbe.dto.request;

import lombok.Data;

@Data
public class ChangePasswordByOwnerRequest {
  private String oldPassword;
  private String newPassword;
}
