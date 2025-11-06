package org.project.createlearnbe.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.Data;
import org.project.createlearnbe.constant.Role;

@Data
public class AccountResponse {
  private UUID id;
  private String email;
  private String username;
  private String phone;

  @Enumerated(EnumType.STRING)
  private Role role;

  private Boolean activated;
}
