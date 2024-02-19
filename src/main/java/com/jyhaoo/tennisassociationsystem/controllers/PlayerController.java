package com.jyhaoo.tennisassociationsystem.controllers;

import com.jyhaoo.tennisassociationsystem.domain.dto.PlayerDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import com.jyhaoo.tennisassociationsystem.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    private final Mapper<PlayerEntity, PlayerDto> playerMapper;

    public PlayerController(PlayerService playerService, Mapper<PlayerEntity, PlayerDto> playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> createPlayer(@PathVariable("id") Long id, @RequestBody PlayerDto playerDto) {
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        boolean playerExists = playerService.isExists(id);
        return null;
    }

    @GetMapping(path = "/players")
    public List<PlayerDto> listPlayers() {
        List<PlayerEntity> players = playerService.findAll();
        return players.stream()
                .map(playerMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable("id") Long id) {
        Optional<PlayerEntity> foundPlayer = playerService.findOne(id);
        return foundPlayer.map(playerEntity -> {
            PlayerDto playerDto = playerMapper.mapTo(playerEntity);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
}
