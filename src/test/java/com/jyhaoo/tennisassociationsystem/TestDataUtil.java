package com.jyhaoo.tennisassociationsystem;

import com.jyhaoo.tennisassociationsystem.domain.dto.PlayerDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;

public final class TestDataUtil {

    public static PlayerEntity createTestPlayerEntityA() {
        return PlayerEntity.builder()
                .id(1L)
                .name("Tom Paul")
                .rating(3.5)
                .build();
    }

    public static PlayerEntity createTestPlayerEntityB() {
        return PlayerEntity.builder()
                .id(2L)
                .name("Jack Sock")
                .rating(4.0)
                .build();
    }

    public static PlayerEntity createTestPlayerEntityC() {
        return PlayerEntity.builder()
                .id(3L)
                .name("Milos Raonic")
                .rating(4.0)
                .build();
    }

    public static PlayerDto createTestPlayerDtoA() {
        return PlayerDto.builder()
                .id(1L)
                .name("Tom Paul")
                .rating(3.5)
                .build();
    }

    public static PlayerDto createTestPlayerDtoB() {
        return PlayerDto.builder()
                .id(2L)
                .name("Jack Sock")
                .rating(4.0)
                .build();
    }

    public static PlayerDto createTestPlayerDtoC() {
        return PlayerDto.builder()
                .id(3L)
                .name("Milos Raonic")
                .rating(4.0)
                .build();
    }
}
