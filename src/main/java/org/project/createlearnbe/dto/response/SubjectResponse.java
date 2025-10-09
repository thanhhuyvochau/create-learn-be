package org.project.createlearnbe.dto.response;

import lombok.Data;

@Data
public class SubjectResponse {
    private Long id;
    private String name;
    private String description;
    private String iconBase64;
}