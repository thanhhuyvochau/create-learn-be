package org.project.createlearnbe.repositories;

import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.entities.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {
  Optional<Clazz> findClazzByIsDisplayedAndId(Boolean isDisplayed, Long id);

  List<Clazz> findAllByIsDisplayed(Boolean isDisplayed);
}
