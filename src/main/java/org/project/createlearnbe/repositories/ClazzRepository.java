package org.project.createlearnbe.repositories;

import java.util.Optional;
import org.project.createlearnbe.entities.Clazz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {
  Optional<Clazz> findClazzByIsDisplayedAndId(Boolean isDisplayed, Long id);

  @Query(
      """
          SELECT c FROM Clazz c
          WHERE c.isDisplayed = true
            AND (:isFree = false OR c.price = 0)
            AND (:gradeId IS NULL OR EXISTS (
                  SELECT g FROM c.grades g WHERE g.id = :gradeId
            ))
            AND (:subjectId IS NULL OR EXISTS (
                  SELECT s FROM c.subjects s WHERE s.id = :subjectId
            ))
          ORDER BY CASE WHEN :isPopular = true THEN c.clickCount END DESC
          """)
  Page<Clazz> findPublicClasses(
      @Param("isFree") boolean isFree,
      @Param("isPopular") boolean isPopular,
      @Param("gradeId") Long gradeId,
      @Param("subjectId") Long subjectId,
      Pageable pageable);
}
