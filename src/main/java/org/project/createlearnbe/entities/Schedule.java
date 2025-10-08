package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "schedules")
@Data
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String time;

  @ManyToOne
  @JoinColumn(name = "clazz_id")
  private Clazz clazz;
}
