package com.jyhaoo.tennisassociationsystem.controllers;

import com.jyhaoo.tennisassociationsystem.domain.dto.PlayerDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import com.jyhaoo.tennisassociationsystem.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    private final Mapper<PlayerEntity, PlayerDto> playerMapper;

    public PlayerController(PlayerService playerService, Mapper<PlayerEntity, PlayerDto> playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @GetMapping(path = "/players")
    public List<PlayerDto> listPlayers() {
        List<PlayerEntity> players = playerService.findAll();
        return players.stream()
                .map(playerMapper::mapTo)
                .collect(Collectors.toList());
    }
}
