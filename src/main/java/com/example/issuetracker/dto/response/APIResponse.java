package com.example.issuetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class APIResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

}
