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

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);
        return new ResponseEntity<>(playerMapper.mapTo(savedPlayerEntity), HttpStatus.CREATED);
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

    @PutMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> fullUpdatePlayer(
            @PathVariable("id") Long id,
            @RequestBody PlayerDto playerDto) {
        if(!playerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerDto.setId(id);
        PlayerEntity savedPlayer = playerService.save(playerMapper.mapFrom(playerDto));
        return new ResponseEntity<>(
                playerMapper.mapTo(savedPlayer),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody PlayerDto playerDto
    ) {
        if (!playerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity updatedPlayerEntity = playerService.partialUpdate(id, playerEntity);
        return new ResponseEntity<>(
                playerMapper.mapTo(updatedPlayerEntity),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/players/{id}")
    public ResponseEntity deletePlayer(@PathVariable("id") Long id) {
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
