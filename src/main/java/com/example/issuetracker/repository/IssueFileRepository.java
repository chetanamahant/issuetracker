package com.example.issuetracker.repository;


import com.example.issuetracker.entity.IssueFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueFileRepository extends JpaRepository<IssueFile, Long> {

    // Get all files for an issue
    List<IssueFile> findByIssueId(Long issueId);
}
