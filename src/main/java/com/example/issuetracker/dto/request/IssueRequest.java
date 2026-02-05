package com.example.issuetracker.dto.request;


import com.example.issuetracker.enums.IssuePriority;
import com.example.issuetracker.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class IssueRequest {

    private String title;
    private String description;
    private IssuePriority priority;
    private IssueStatus status;
    // ✅ For assignment
    private Long assignedUserId;
    private Long projectId;
}