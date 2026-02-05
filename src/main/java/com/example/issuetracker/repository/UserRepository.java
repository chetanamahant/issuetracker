package com.example.issuetracker.repository;

import com.example.issuetracker.entity.User;
import com.example.issuetracker.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByRole(Role role);

}