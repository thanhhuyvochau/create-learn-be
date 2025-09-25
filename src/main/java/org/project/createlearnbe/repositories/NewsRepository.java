package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
  Optional<News> findByIsDisplayAndId(Boolean isDisplay, Long id);

  List<News> findAllByIsDisplay(Boolean isDisplay);
}
