package com.example.issuetracker.repository;

import com.example.issuetracker.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
