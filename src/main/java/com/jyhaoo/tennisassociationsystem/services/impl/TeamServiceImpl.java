package com.jyhaoo.tennisassociationsystem.services.impl;

import com.jyhaoo.tennisassociationsystem.repositories.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl {

    private final TeamRepository teamRepository;

    public TeamServiceImpl (TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
