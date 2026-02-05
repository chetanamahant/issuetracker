package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.dto.request.TeamRequest;
import com.example.issuetracker.entity.Team;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.TeamRepository;
import com.example.issuetracker.repository.UserRepository;
import com.example.issuetracker.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    /* ===================== CREATE TEAM ===================== */

    @Override
    public Team createTeam(TeamRequest request) {

        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setDescription(request.getDescription());

        return teamRepository.save(team);
    }

    /* ===================== UPDATE TEAM ===================== */

    @Override
    public Team updateTeam(Long teamId, TeamRequest request) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        team.setTeamName(request.getTeamName());
        team.setDescription(request.getDescription());

        return teamRepository.save(team);
    }

    /* ===================== ADD MEMBER ===================== */

    @Override
    public Team addMember(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        team.getMembers().add(user);

        return teamRepository.save(team);
    }

    /* ===================== REMOVE MEMBER ===================== */

    @Override
    public Team removeMember(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        team.getMembers().remove(user);

        return teamRepository.save(team);
    }
}