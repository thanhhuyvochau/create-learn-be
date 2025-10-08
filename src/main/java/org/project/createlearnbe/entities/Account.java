package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.project.createlearnbe.constant.Role;

import java.util.UUID;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    private String username;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean activated;
}
