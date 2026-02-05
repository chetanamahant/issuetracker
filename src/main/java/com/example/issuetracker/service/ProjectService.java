package com.example.issuetracker.service;

import com.example.issuetracker.dto.request.CommentRequest;
import com.example.issuetracker.dto.request.ProjectRequest;
import com.example.issuetracker.dto.response.CommentResponse;
import com.example.issuetracker.dto.response.ProjectResponse;
import com.example.issuetracker.entity.Project;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    List<ProjectResponse> getAllProjects();
}