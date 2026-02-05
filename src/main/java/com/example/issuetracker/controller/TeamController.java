package com.example.issuetracker.controller;

import com.example.issuetracker.dto.request.MemberRequest;
import com.example.issuetracker.dto.request.TeamRequest;
import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.entity.Team;
import com.example.issuetracker.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/teams")
@Tag(
        name = "Team APIs",
        description = "APIs for managing teams and team members"
)
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // ✅ CREATE TEAM
    @Operation(
            summary = "Create team",
            description = "Creates a new team"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public APIResponse  <Team> createTeam(@RequestBody TeamRequest request) {

        return new APIResponse<>(
                true,
                "Team created successfully",
                teamService.createTeam(request),
                LocalDateTime.now()
        );
    }





     // ✅ UPDATE TEAM
     @Operation(
        summary = "Update team",
        description = "Updates an existing team"
     )
     @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Team updated successfully"),
        @ApiResponse(responseCode = "404", description = "Team not found")
     })
     @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
     @PutMapping("/{teamId}")
     public APIResponse<Team> updateTeam(
        @PathVariable Long teamId,
        @RequestBody TeamRequest request) {

          return new APIResponse<>(
             true,
             "Team updated successfully",
              teamService.updateTeam(teamId, request),
              LocalDateTime.now()
          );
        }


     // ✅ ADD MEMBER TO TEAM
      @Operation(
        summary = "Add team member",
        description = "Adds a user as a member of a team"
       )
      @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member added successfully"),
            @ApiResponse(responseCode = "404", description = "Team or user not found")
      })
      @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
      @PostMapping("/{teamId}/members")
       public APIResponse<Team> addMember(
         @PathVariable Long teamId,
         @RequestBody MemberRequest request) {

        return new APIResponse<>(
            true,
            "Member added to team",
            teamService.addMember(teamId, request.getUserId()),
            LocalDateTime.now()
         );
       }

        // ✅ REMOVE MEMBER FROM TEAM
        @Operation(
              summary = "Remove team member",
              description = "Removes a user from a team"
        )
         @ApiResponses({
                 @ApiResponse(responseCode = "200", description = "Member removed successfully"),
                 @ApiResponse(responseCode = "404", description = "Team or user not found")
         })
        @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
        @DeleteMapping("/{teamId}/members/{userId}")
        public APIResponse<Team> removeMember(
                              @PathVariable Long teamId, @PathVariable Long userId) {

              return new APIResponse<>(
                           true,
                          "Member removed from team",
                            teamService.removeMember(teamId, userId),
                            LocalDateTime.now()
                       );
        }
}
