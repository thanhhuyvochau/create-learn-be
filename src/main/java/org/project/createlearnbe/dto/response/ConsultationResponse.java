package org.project.createlearnbe.dto.response;


import lombok.Data;

@Data
public class ConsultationResponse {
    private Long id;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String content;
}