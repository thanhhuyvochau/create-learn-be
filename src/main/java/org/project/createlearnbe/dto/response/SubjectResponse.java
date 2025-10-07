package org.project.createlearnbe.dto.response;

import lombok.Data;

@Data
public class SubjectResponse {
    private long id;
    private String name;
    private String description;
    private String iconBase64;
}