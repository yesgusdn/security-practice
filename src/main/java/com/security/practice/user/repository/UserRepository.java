package com.security.practice.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.practice.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findById(Long id);
	boolean existsByUsername(String username);
}
