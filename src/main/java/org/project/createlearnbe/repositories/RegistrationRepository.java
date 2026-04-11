package org.project.createlearnbe.repositories;

import org.project.createlearnbe.constant.ProcessStatus;
import org.project.createlearnbe.entities.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  @Query(
      """
      SELECT r FROM Registration r
      WHERE r.status != 'CLASS_DELETED'
      ORDER BY
        CASE WHEN r.status = 'PROCESSING' THEN 0 ELSE 1 END,
        r.status ASC,
        r.createdAt DESC
      """)
  Page<Registration> findAllSortedByStatusAndCreatedAt(Pageable pageable);

  @Query(
      """
      SELECT r FROM Registration r
      WHERE r.status = :status
      ORDER BY r.createdAt DESC
      """)
  Page<Registration> findByStatus(@Param("status") ProcessStatus status, Pageable pageable);

  @Query(
      """
      SELECT r FROM Registration r
      WHERE r.status != 'CLASS_DELETED'
        AND (
          LOWER(r.customerName) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(r.customerEmail) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(r.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))
        )
      ORDER BY
        CASE WHEN r.status = 'PROCESSING' THEN 0 ELSE 1 END,
        r.status ASC,
        r.createdAt DESC
      """)
  Page<Registration> findBySearch(@Param("search") String search, Pageable pageable);
}
