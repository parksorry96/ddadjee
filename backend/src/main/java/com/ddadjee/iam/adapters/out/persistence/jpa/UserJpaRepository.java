package com.ddadjee.iam.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByUsername(String username);
}
