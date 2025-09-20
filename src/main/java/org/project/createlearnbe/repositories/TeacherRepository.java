package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}