package com.example.issuetracker.service;

import com.example.issuetracker.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileResponse uploadFile(Long issueId, MultipartFile file);
}

