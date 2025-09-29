package org.project.createlearnbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.createlearnbe.constant.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDto {
  private long id;
  private String firstName;
  private String lastName;
  private String introduction;
  private Gender gender;
  private String profileImageUrl;
}
