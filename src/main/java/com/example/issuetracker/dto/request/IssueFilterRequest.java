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

public class IssueFilterRequest {

    private IssueStatus status;
    private IssuePriority priority;
    private Long assignedUserId;
    private Long projectId;
    private String keyword;
}
