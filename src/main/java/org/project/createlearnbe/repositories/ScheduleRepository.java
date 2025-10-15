package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  Page<Schedule> findAllByClazz_Id(Long classId, Pageable pageable);
}
