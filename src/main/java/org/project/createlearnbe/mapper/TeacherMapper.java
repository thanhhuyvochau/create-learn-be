package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.TeacherRequestDto;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.entities.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponseDto toDto(Teacher teacher);

    Teacher toEntity(TeacherRequestDto dto);

    void updateEntityFromDto(TeacherRequestDto dto, @MappingTarget Teacher teacher);
}