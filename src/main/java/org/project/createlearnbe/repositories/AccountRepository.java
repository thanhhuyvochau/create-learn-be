package org.project.createlearnbe.repositories;

import java.util.Optional;
import java.util.UUID;
import org.project.createlearnbe.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);

  @Query(
      """
      SELECT a FROM Account a
      WHERE LOWER(a.username) LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(a.email) LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(a.phone) LIKE LOWER(CONCAT('%', :search, '%'))
      """)
  Page<Account> findBySearch(@Param("search") String search, Pageable pageable);
}
