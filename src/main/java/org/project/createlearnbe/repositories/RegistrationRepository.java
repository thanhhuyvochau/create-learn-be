package org.project.createlearnbe.repositories;

import org.project.createlearnbe.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {}
