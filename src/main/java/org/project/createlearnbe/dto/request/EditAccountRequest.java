package org.project.createlearnbe.dto.request;

import lombok.Data;
import org.project.createlearnbe.constant.Role;

@Data
public class EditAccountRequest {
  private String password;
  private Role role;
  private Boolean activated;
  private String email;
  private String username;
  private String phone;
}
