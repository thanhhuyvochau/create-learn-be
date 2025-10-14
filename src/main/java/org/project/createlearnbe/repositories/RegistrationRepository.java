package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  @Query(
      """
      SELECT r FROM Registration r
      ORDER BY
        CASE WHEN r.status = 'PROCESSING' THEN 0 ELSE 1 END,
        r.status ASC,
        r.createdAt DESC
      """)
  Page<Registration> findAllSortedByStatusAndCreatedAt(Pageable pageable);
}
