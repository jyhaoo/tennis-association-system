package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {


    List<PlayerEntity> findAll();

    Optional<PlayerEntity> findOne(Long id);

    boolean isExists(Long id);
}
