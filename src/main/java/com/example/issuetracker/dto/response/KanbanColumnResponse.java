package com.example.issuetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KanbanColumnResponse {

    private String status; // TODO / IN_PROGRESS / DONE
    private List<IssueResponse> issues;
}
