package org.project.createlearnbe.dto.request;

import lombok.Data;
import org.project.createlearnbe.constant.Role;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String phone;
    private Boolean activated;
    private Role role;
}
