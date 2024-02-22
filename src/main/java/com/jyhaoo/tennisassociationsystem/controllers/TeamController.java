package com.jyhaoo.tennisassociationsystem.controllers;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.services.TeamService;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/teams")
    public ResponseEntity<List<TeamDto>> listTeams() {
        List<TeamEntity> teams = teamService.findAll();
        return new ResponseEntity<>(teams.stream()
                .map(teamMapper::mapTo)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/teams/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable("id") Long id) {
        Optional<TeamEntity> foundTeam = teamService.findOne(id);
        return foundTeam.map(teamEntity -> {
            TeamDto teamDto = teamMapper.mapTo(teamEntity);
            return new ResponseEntity<>(teamDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping(path = "/teams/{id}")
    public ResponseEntity<TeamDto> fullUpdateTeam(
            @PathVariable("id") Long id,
            @RequestBody TeamDto teamDto
    ) {
        if(!teamService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        teamDto.setId(id);
        TeamEntity savedTeam = teamService.save(teamMapper.mapFrom(teamDto));
        return new ResponseEntity<>(teamMapper.mapTo(savedTeam), HttpStatus.OK);
    }

    @PatchMapping(path = "/teams/{id}")
    public ResponseEntity<TeamDto> partialUpdateTeam(
            @PathVariable("id") Long id,
            @RequestBody TeamDto teamDto
    ) {
        if(!teamService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TeamEntity teamEntity = teamMapper.mapFrom(teamDto);
        TeamEntity savedTeamEntity = teamService.partialUpdate(id, teamEntity);
        return new ResponseEntity<>(teamMapper.mapTo(savedTeamEntity), HttpStatus.OK);
    }
}
