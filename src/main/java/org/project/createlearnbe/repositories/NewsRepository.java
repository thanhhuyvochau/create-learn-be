package org.project.createlearnbe.repositories;

import java.util.Optional;
import org.project.createlearnbe.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
  Optional<News> findByIsDisplayAndId(Boolean isDisplay, Long id);

  Page<News> findAllByIsDisplay(Boolean isDisplay, Pageable pageable);
}
