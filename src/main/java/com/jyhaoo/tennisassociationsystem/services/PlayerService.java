package com.jyhaoo.tennisassociationsystem.services;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;

import java.util.List;

public interface PlayerService {


    List<PlayerEntity> findAll();
}
