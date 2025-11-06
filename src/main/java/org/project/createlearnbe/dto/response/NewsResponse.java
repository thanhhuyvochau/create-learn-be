package org.project.createlearnbe.dto.response;

import lombok.Data;

@Data
public class NewsResponse {
  private Long id;
  private String title;
  private String brief;
  private String content;
  private Boolean isDisplay;
  private String image;
}
