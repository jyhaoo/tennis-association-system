package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    PlayerEntity save(PlayerEntity playerEntity);

    List<PlayerEntity> findAll();

    Page<PlayerEntity> findAll(Pageable pageable);

    Optional<PlayerEntity> findOne(Long id);

    boolean exists(Long id);

    PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity);

    void deletePlayer(Long id);
}
