package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.dto.request.CommentRequest;
import com.example.issuetracker.dto.response.CommentResponse;
import com.example.issuetracker.entity.Comment;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.CommentRepository;
import com.example.issuetracker.repository.IssueRepository;
import com.example.issuetracker.repository.UserRepository;
import com.example.issuetracker.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    // ✅ ADD COMMENT
    @Override
    public CommentResponse addComment(CommentRequest request, String username) {

        Issue issue = issueRepository.findById(request.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .issue(issue)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);
        return mapToResponse(saved);
    }

    // ✅ GET COMMENTS BY ISSUE
    @Override
    public List<CommentResponse> getCommentsByIssue(Long issueId) {

        return commentRepository.findByIssue_Id(issueId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ UPDATE COMMENT
    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest request, String username) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can update only your own comment");
        }

        comment.setContent(request.getContent());
        Comment updated = commentRepository.save(comment);

        return mapToResponse(updated);
    }

    // ✅ DELETE COMMENT
    @Override
    public void deleteComment(Long commentId, String username) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can delete only your own comment");
        }

        commentRepository.delete(comment);
    }

    // 🔁 MAPPER
    private CommentResponse mapToResponse(Comment comment) {

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .commentedBy(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

