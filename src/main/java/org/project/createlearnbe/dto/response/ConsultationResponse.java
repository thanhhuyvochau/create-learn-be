package org.project.createlearnbe.dto.response;


import lombok.Data;
import org.project.createlearnbe.constant.ProcessStatus;

@Data
public class ConsultationResponse {
    private Long id;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String content;
    private ProcessStatus status;
}