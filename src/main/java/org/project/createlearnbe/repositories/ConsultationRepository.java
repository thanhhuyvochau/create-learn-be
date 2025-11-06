package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
  @Query(
      """
      SELECT c FROM Consultation c
      ORDER BY
        CASE WHEN c.status = 'PROCESSING' THEN 0 ELSE 1 END,
        c.status ASC,
        c.createdAt DESC
      """)
  Page<Consultation> findAllSortedByStatusAndCreatedAt(Pageable pageable);
}
