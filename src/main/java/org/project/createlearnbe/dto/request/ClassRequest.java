package org.project.createlearnbe.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClassRequest {
  private String name;
  private String brief;
  private String description;
  private String image;
  private String requirement;
  private String guarantee;
  private Boolean isDisplayed;
  private List<Long> subjectIds;
  private List<Long> gradeIds;
  private Long teacherId;
  private BigDecimal price;
}
