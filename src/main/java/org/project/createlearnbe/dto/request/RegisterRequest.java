package org.project.createlearnbe.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String phone;
}
