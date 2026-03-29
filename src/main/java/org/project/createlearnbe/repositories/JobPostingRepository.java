package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

  Page<JobPosting> findAllByIsDeletedFalse(Pageable pageable);

  @Query("""
      SELECT j FROM JobPosting j
      WHERE j.isDeleted = false
        AND j.isActive = true
        AND (:department IS NULL OR j.department = :department)
        AND (:location IS NULL OR j.location = :location)
      """)
  Page<JobPosting> findPublicJobPostings(
      @Param("department") String department,
      @Param("location") String location,
      Pageable pageable);
}
