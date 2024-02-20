package com.jyhaoo.tennisassociationsystem.services.impl;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import com.jyhaoo.tennisassociationsystem.repositories.TeamRepository;
import com.jyhaoo.tennisassociationsystem.services.TeamService;
import org.springframework.stereotype.Service;

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
}
