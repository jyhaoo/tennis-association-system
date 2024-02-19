package com.jyhaoo.tennisassociationsystem;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;

public final class TestDataUtil {

    public static PlayerEntity createTestPlayerEntityA() {
        return PlayerEntity.builder()
                .id(1L)
                .name("Tom Adam")
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
}
