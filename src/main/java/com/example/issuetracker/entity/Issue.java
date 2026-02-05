package com.example.issuetracker.entity;


import com.example.issuetracker.enums.IssuePriority;
import com.example.issuetracker.enums.IssueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @ManyToOne
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
