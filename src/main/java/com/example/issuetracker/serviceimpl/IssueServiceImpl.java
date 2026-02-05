package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.config.SecurityUtils;
import com.example.issuetracker.dto.request.IssueFilterRequest;
import com.example.issuetracker.dto.request.IssueRequest;
import com.example.issuetracker.dto.request.IssueUpdateRequest;
import com.example.issuetracker.dto.response.IssueResponse;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.entity.Project;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.IssueRepository;
import com.example.issuetracker.repository.ProjectRepository;
import com.example.issuetracker.repository.UserRepository;
import com.example.issuetracker.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;


    /* ===================== CREATE ISSUE ===================== */

    @Override
    public IssueResponse createIssue(IssueRequest request) {

        User assignee = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // 🔐 1. Get logged-in username from JWT
        String username = SecurityUtils.getCurrentUsername();

        // 🔎 2. Fetch User entity
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + username)
                );
        Issue issue = Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus())
                .assignedTo(assignee)
                .createdBy(currentUser)
                .project(project)
                .createdAt(LocalDateTime.now())
                .build();

        issueRepository.save(issue);
        return map(issue);
    }

    /* ===================== UPDATE ISSUE ===================== */

    @Override
    public IssueResponse updateIssue(Long id, IssueUpdateRequest request) {

        User assignee = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));


        // 🔐 1. Get logged-in username from JWT
        String username = SecurityUtils.getCurrentUsername();

        // 🔎 2. Fetch User entity
        User loginUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + username)
                );
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
          System.out.println("----------*************loginuser : - " +loginUser);
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setPriority(request.getPriority());
        issue.setStatus(request.getStatus());
        issue.setUpdatedAt(LocalDateTime.now());
        issue.setAssignedTo(assignee);
        issue.setProject(project);
        issue.setCreatedBy(loginUser);


        issueRepository.save(issue);
        return map(issue);
    }

    /* ===================== FILTER & SEARCH ===================== */

    @Override
    public List<IssueResponse> getIssuesByFilter(IssueFilterRequest filter) {

        List<Issue> issues = issueRepository.findAll();

        if (filter.getStatus() != null) {
            issues = issues.stream()
                    .filter(i -> i.getStatus() == filter.getStatus())
                    .toList();
        }

        if (filter.getPriority() != null) {
            issues = issues.stream()
                    .filter(i -> i.getPriority() == filter.getPriority())
                    .toList();
        }

        if (filter.getAssignedUserId() != null) {
            issues = issues.stream()
                    .filter(i ->
                            i.getAssignedTo() != null &&
                                    i.getAssignedTo().getId().equals(filter.getAssignedUserId())
                    )
                    .toList();
        }

        if (filter.getProjectId() != null) {
            issues = issues.stream()
                    .filter(i ->
                            i.getProject() != null &&
                                    i.getProject().getId().equals(filter.getProjectId())
                    )
                    .toList();
        }

        if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
            String key = filter.getKeyword().toLowerCase();
            issues = issues.stream()
                    .filter(i ->
                            i.getTitle().toLowerCase().contains(key) ||
                                    i.getDescription().toLowerCase().contains(key)
                    )
                    .toList();
        }

        return issues.stream()
                .map(this::map)
                .toList();
    }


    @Override
    public void deleteIssue(Long id) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        issueRepository.delete(issue);
    }

    /* ===================== ASSIGN ISSUE ===================== */

    @Override
    public IssueResponse assignIssue(Long issueId, Long userId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        issue.setAssignedTo(user);
        issueRepository.save(issue);

        return map(issue);
    }

    /* ===================== MAPPER ===================== */

    private IssueResponse map(Issue issue) {

        return IssueResponse.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .priority(issue.getPriority().name())
                .status(issue.getStatus().name())
                .assignedTo(
                        issue.getAssignedTo() != null
                                ? issue.getAssignedTo().getUsername()
                                : null
                )
                .projectName(issue.getProject().getName())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .createdBy(issue.getCreatedBy().getUsername())
                .build();
    }
}





