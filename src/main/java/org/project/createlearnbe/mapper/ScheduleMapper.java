package org.project.createlearnbe.mapper;

import org.mapstruct.*;
import org.project.createlearnbe.dto.request.ScheduleRequest;
import org.project.createlearnbe.dto.response.ScheduleResponse;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.entities.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

  @Mapping(target = "clazz", ignore = true)
  Schedule toEntity(ScheduleRequest request);

  ScheduleResponse toResponse(Schedule schedule);

  @AfterMapping
  default void linkClazz(
      @MappingTarget Schedule schedule, ScheduleRequest request, @Context Clazz clazz) {
    schedule.setClazz(clazz);
  }

  void updateScheduleFromRequest(ScheduleRequest request, @MappingTarget Schedule schedule);
}
