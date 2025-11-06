package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.dto.request.ScheduleRequest;
import org.project.createlearnbe.dto.response.ScheduleResponse;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.entities.Schedule;
import org.project.createlearnbe.mapper.ScheduleMapper;
import org.project.createlearnbe.repositories.ClazzRepository;
import org.project.createlearnbe.repositories.ScheduleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final ClazzRepository clazzRepository;
  private final ScheduleMapper scheduleMapper;

  public ScheduleResponse create(ScheduleRequest request) {
    Clazz clazz =
        clazzRepository
            .findById(request.getClazzId())
            .orElseThrow(
                () -> new RuntimeException("Clazz not found with id " + request.getClazzId()));

    Schedule schedule = scheduleMapper.toEntity(request);
    scheduleMapper.linkClazz(schedule, request, clazz);
    scheduleRepository.save(schedule);
    return scheduleMapper.toResponse(schedule);
  }

  public ScheduleResponse update(Long id, ScheduleRequest request) {
    Schedule schedule =
        scheduleRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));

    Clazz clazz =
        clazzRepository
            .findById(request.getClazzId())
            .orElseThrow(
                () -> new RuntimeException("Clazz not found with id " + request.getClazzId()));

    schedule.setTime(request.getTime());
    scheduleRepository.save(schedule);
    return scheduleMapper.toResponse(schedule);
  }

  public void delete(Long id) {
    if (!scheduleRepository.existsById(id)) {
      throw new RuntimeException("Schedule not found with id " + id);
    }
    scheduleRepository.deleteById(id);
  }

  public ApiPage<ScheduleResponse> getByClassId(Long clazzId, Pageable pageable) {
    return ApiPage.of(
        scheduleRepository.findAllByClazz_Id(clazzId, pageable).map(scheduleMapper::toResponse));
  }

  public ApiPage<ScheduleResponse> getAll(Pageable pageable) {
    return ApiPage.of(scheduleRepository.findAll(pageable).map(scheduleMapper::toResponse));
  }
}
