package com.example.issuetracker.service;

import com.example.issuetracker.dto.request.CommentRequest;
import com.example.issuetracker.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(CommentRequest request, String username);

    List<CommentResponse> getCommentsByIssue(Long issueId);

    CommentResponse updateComment(Long commentId, CommentRequest request, String username);

    void deleteComment(Long commentId, String username);
}