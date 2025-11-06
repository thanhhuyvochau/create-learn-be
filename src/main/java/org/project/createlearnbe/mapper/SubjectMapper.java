package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.SubjectRequest;
import org.project.createlearnbe.dto.response.SubjectResponse;
import org.project.createlearnbe.entities.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
  Subject toEntity(SubjectRequest request);

  SubjectResponse toResponse(Subject subject);

  void updateEntityFromRequest(SubjectRequest request, @MappingTarget Subject subject);
}
