package com.example.issuetracker.repository;

import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.enums.IssuePriority;
import com.example.issuetracker.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository  extends JpaRepository<Issue, Long> {
    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByPriority(IssuePriority priority);

    List<Issue> findByAssignedTo_Id(Long userId);

    List<Issue> findByProject_Id(Long projectId);

    // 🔍 Search
    List<Issue> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description
    );
}