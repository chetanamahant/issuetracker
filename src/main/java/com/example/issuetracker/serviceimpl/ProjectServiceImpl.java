package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.config.SecurityUtils;
import com.example.issuetracker.dto.request.CommentRequest;
import com.example.issuetracker.dto.request.ProjectRequest;
import com.example.issuetracker.dto.response.CommentResponse;
import com.example.issuetracker.dto.response.ProjectResponse;
import com.example.issuetracker.entity.Comment;
import com.example.issuetracker.entity.Project;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.ProjectRepository;
import com.example.issuetracker.repository.UserRepository;
import com.example.issuetracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        // 🔐 1. Get logged-in username from JWT
        String username = SecurityUtils.getCurrentUsername();

        // 🔎 2. Fetch User entity
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + username)
                );

        Project project = Project.builder()
                .name(request.getName())
                .createdBy(currentUser)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        projectRepository.save(project);

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .createdBy(currentUser.getUsername())
                .build();
    }

    @Override
    public List<ProjectResponse> getAllProjects() {



        return projectRepository.findAll()
                .stream()
                .map(p -> ProjectResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .createdAt(p.getCreatedAt())
                        .createdBy(
                                p.getCreatedBy() != null
                                        ? p.getCreatedBy().getUsername()
                                        : null
                        )
                        .build())
                .toList();
    }


}
