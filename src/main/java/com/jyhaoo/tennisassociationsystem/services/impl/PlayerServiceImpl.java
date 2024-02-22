package com.jyhaoo.tennisassociationsystem.services.impl;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.repositories.PlayerRepository;
import com.jyhaoo.tennisassociationsystem.services.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl (PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerEntity save(PlayerEntity playerEntity) {
        return playerRepository.save(playerEntity);
    }

    @Override
    public List<PlayerEntity> findAll() {
        return StreamSupport.stream(playerRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PlayerEntity> findAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    @Override
    public Optional<PlayerEntity> findOne(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return playerRepository.existsById(id);
    }

    @Override
    public PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity) {
        playerEntity.setId(id);

        return playerRepository.findById(id).map(existingPlayer -> {
            Optional.ofNullable(playerEntity.getName()).ifPresent(existingPlayer::setName);
            Optional.ofNullable(playerEntity.getRating()).ifPresent(existingPlayer::setRating);
            return playerRepository.save(existingPlayer);
        }).orElseThrow(() -> new RuntimeException("Player does not exist"));
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
