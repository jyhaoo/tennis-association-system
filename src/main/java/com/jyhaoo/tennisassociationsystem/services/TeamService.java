package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    TeamEntity save(TeamEntity teamEntity);

    List<TeamEntity> findAll();

    Page<TeamEntity> findAll(Pageable pageable);

    Optional<TeamEntity> findOne(Long id);

    boolean exists(Long id);

    TeamEntity partialUpdate(Long id, TeamEntity teamEntity);

    void delete(Long id);
}
