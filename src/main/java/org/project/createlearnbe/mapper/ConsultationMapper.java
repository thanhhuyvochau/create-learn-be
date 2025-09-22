package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.ConsultationRequest;
import org.project.createlearnbe.dto.response.ConsultationResponse;
import org.project.createlearnbe.entities.Consultation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    Consultation toEntity(ConsultationRequest request);

    ConsultationResponse toResponse(Consultation entity);

    List<ConsultationResponse> toResponseList(List<Consultation> entities);

    void updateEntityFromRequest(ConsultationRequest request, @MappingTarget Consultation entity);
}