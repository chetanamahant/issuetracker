package com.example.issuetracker.service;

import com.example.issuetracker.dto.request.IssueFilterRequest;
import com.example.issuetracker.dto.request.IssueRequest;
import com.example.issuetracker.dto.request.IssueUpdateRequest;
import com.example.issuetracker.dto.response.IssueResponse;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    IssueResponse createIssue(IssueRequest request);

    IssueResponse updateIssue(Long id, IssueUpdateRequest request);

    List<IssueResponse> getIssuesByFilter(IssueFilterRequest filter);

    IssueResponse assignIssue(Long issueId, Long userId);

    // ✅ DELETE ISSUE
    void deleteIssue(Long issueId);
}
