package org.project.createlearnbe.repositories;

import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.entities.Clazz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {
  Optional<Clazz> findClazzByIsDisplayedAndId(Boolean isDisplayed, Long id);

  Page<Clazz> findAllByIsDisplayed(Boolean isDisplayed, Pageable pageable);
  Page<Clazz> findAllByIsDisplayedAndPriceEquals(Boolean isDisplayed, BigDecimal price, Pageable pageable);
  Page<Clazz> findTopByIsDisplayedOrderByClickCountDesc(Boolean isDisplayed, Pageable pageable);
}
