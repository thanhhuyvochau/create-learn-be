package org.project.createlearnbe.dto.request;

import lombok.Data;

@Data
public class NewsRequest {
    private String title;
    private String brief;
    private String content;
    private Boolean isDisplay;
    private String image;
}
