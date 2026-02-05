package com.example.issuetracker.service;

import com.example.issuetracker.dto.request.TeamRequest;
import com.example.issuetracker.entity.Team;

public interface TeamService {

    Team createTeam(TeamRequest request);

    Team updateTeam(Long teamId, TeamRequest request);

    Team addMember(Long teamId, Long userId);

    Team removeMember(Long teamId, Long userId);
}
