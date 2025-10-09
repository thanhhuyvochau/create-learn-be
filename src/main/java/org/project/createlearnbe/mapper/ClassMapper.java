
package org.project.createlearnbe.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.mapper.GradeMapper;
import org.project.createlearnbe.mapper.SubjectMapper;
import org.project.createlearnbe.mapper.TeacherMapper;
import org.project.createlearnbe.mapper.ScheduleMapper;

import org.project.createlearnbe.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                SubjectMapper.class,
                GradeMapper.class,
                TeacherMapper.class,
                ScheduleMapper.class
        }
)
public abstract class ClassMapper {

    @Autowired
    protected UrlUtils urlUtils;

    public abstract ClassResponse toResponse(Clazz clazz);

    @AfterMapping
    protected void enrich(@MappingTarget ClassResponse dto, Clazz clazz) {
        if (clazz == null) return;
        if (clazz.getImage() != null && !clazz.getImage().isBlank()) {
            dto.setImage(urlUtils.buildAbsolutePath(clazz.getImage()));
        }
        // Ensure non-null collections (MapStruct may set null if source null)
        if (dto.getSubjects() == null) dto.setSubjects(java.util.List.of());
        if (dto.getGrades() == null) dto.setGrades(java.util.List.of());
        if (dto.getScheduleResponses() == null) dto.setScheduleResponses(java.util.List.of());
    }
}
