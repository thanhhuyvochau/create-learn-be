package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
  boolean existsByName(String name);
}
