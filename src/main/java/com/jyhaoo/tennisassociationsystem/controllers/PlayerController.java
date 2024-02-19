package com.jyhaoo.tennisassociationsystem.controllers;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "/players")
    public List<PlayerEntity> listPlayers() {
        return playerService.findAll();
    }
}
