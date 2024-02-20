package com.jyhaoo.tennisassociationsystem.controllers;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.services.TeamService;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamService teamService;

    private Mapper<TeamEntity, TeamDto> teamMapper;

    public TeamController(TeamService teamService, Mapper<TeamEntity, TeamDto> teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @PostMapping(path = "/teams")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        TeamEntity teamEntity = teamMapper.mapFrom(teamDto);
        TeamEntity savedTeamEntity = teamService.save(teamEntity);
        return new ResponseEntity<>(teamMapper.mapTo(savedTeamEntity), HttpStatus.CREATED);
    }
}
