package org.project.createlearnbe.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "class")
@Data
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brief;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String image;
    private String requirement;
    private String optional;
}
