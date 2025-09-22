package org.project.createlearnbe.dto.request;

import lombok.Data;

@Data
public class ConsultationRequest {
    private String customerName;
    private String phoneNumber;
    private String email;
    private String content;
}