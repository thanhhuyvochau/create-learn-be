package org.project.createlearnbe.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClassResponse {
  private Long id;
  private String name;
  private String brief;
  private String description;
  private String image;
  private String requirement;
  private String guarantee;
  private Boolean isDisplayed;
  private List<SubjectResponse> subjects;
  private List<GradeResponse> grades;
  private TeacherResponseDto teacher;
  private BigDecimal price;
}
