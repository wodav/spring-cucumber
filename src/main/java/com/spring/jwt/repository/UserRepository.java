package com.spring.jwt.repository;

import com.spring.jwt.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
}
