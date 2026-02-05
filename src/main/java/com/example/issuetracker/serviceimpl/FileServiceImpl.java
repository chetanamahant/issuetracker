package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.dto.response.FileResponse;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.entity.IssueFile;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.IssueFileRepository;
import com.example.issuetracker.repository.IssueRepository;
import com.example.issuetracker.service.FileService;
import com.example.issuetracker.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final IssueRepository issueRepository;
    private final IssueFileRepository fileRepository;

    @Override
    public FileResponse uploadFile(Long issueId, MultipartFile file) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        // ✅ VALIDATION
        FileUploadUtil.validateFile(file);

        IssueFile issueFile = IssueFile.builder()
                .fileName(FileUploadUtil.getFileName(file))
                .fileType(FileUploadUtil.getFileType(file))
                .fileSize(FileUploadUtil.getFileSize(file))
                .data(FileUploadUtil.getFileBytes(file))
                .issue(issue)
                .uploadedAt(LocalDateTime.now())
                .build();

        fileRepository.save(issueFile);

        return map(issueFile);
    }

    private FileResponse map(IssueFile file) {
        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .uploadedAt(file.getUploadedAt())
                .build();
    }
}

