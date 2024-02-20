package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    PlayerEntity save(PlayerEntity playerEntity);

    List<PlayerEntity> findAll();

    Optional<PlayerEntity> findOne(Long id);

    boolean exists(Long id);
}
