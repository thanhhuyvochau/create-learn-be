package org.project.createlearnbe.constant;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Role {
  ADMIN("Administrator", "ADMIN"),
  OPERATOR("Operator", "OPERATOR");

  private final String name;
  private final String code;

  Role(String name, String code) {
    this.name = name;
    this.code = code;
  }
}
