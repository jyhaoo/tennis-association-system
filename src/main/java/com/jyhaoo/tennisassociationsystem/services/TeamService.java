package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    TeamEntity save(TeamEntity teamEntity);

    List<TeamEntity> findAll();

    Optional<TeamEntity> findOne(Long id);
}
