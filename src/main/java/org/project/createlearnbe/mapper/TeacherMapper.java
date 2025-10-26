package org.project.createlearnbe.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.TeacherRequestDto;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.entities.Teacher;
import org.project.createlearnbe.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TeacherMapper {

    @Autowired
    protected UrlUtils urlUtils;

    public abstract TeacherResponseDto toDto(Teacher teacher);

    public abstract Teacher toEntity(TeacherRequestDto dto);

    public abstract void updateEntityFromDto(TeacherRequestDto dto, @MappingTarget Teacher teacher);

    @AfterMapping
    protected void afterMapping(Teacher entity, @MappingTarget TeacherResponseDto dto) {
        dto.setProfileImageUrl(
                urlUtils.buildAbsolutePath(entity.getProfileImageUrl())
        );
    }
}
