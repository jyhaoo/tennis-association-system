package com.jyhaoo.tennisassociationsystem.services.impl;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import com.jyhaoo.tennisassociationsystem.repositories.TeamRepository;
import com.jyhaoo.tennisassociationsystem.services.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;


    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamEntity save(TeamEntity teamEntity) {
        return teamRepository.save(teamEntity);
    }

    @Override
    public List<TeamEntity> findAll() {
        return StreamSupport.stream(teamRepository
                .findAll()
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeamEntity> findOne(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return teamRepository.existsById(id);
    }

    @Override
    public TeamEntity partialUpdate(Long id, TeamEntity teamEntity) {
        teamEntity.setId(id);
        return teamRepository.findById(id).map(existingTeam -> {
            Optional.ofNullable(teamEntity.getName()).ifPresent(existingTeam::setName);
            return teamRepository.save(existingTeam);
        }).orElseThrow(() -> new RuntimeException("Team does not exist"));
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }
}
