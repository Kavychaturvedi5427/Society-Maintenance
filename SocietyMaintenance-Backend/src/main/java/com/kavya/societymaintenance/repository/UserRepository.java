package com.kavya.societymaintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kavya.societymaintenance.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String username);
}
