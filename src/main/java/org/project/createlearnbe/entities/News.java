package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "news")
@Data
public class News {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String title;
  private String brief;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String content;

  private Boolean isDisplay;
  private String image;
}
