package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.GradeRequest;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.entities.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    Grade toEntity(GradeRequest request);

    GradeResponse toResponse(Grade grade);

    void updateEntityFromRequest(GradeRequest request, @MappingTarget Grade grade);
}