package com.jyhaoo.tennisassociationsystem.mappers.impl;

import com.jyhaoo.tennisassociationsystem.domain.dto.PlayerDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapperImpl implements Mapper<PlayerEntity, PlayerDto> {

    private final ModelMapper modelMapper;

    public PlayerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PlayerDto mapTo(PlayerEntity playerEntity) {
        return modelMapper.map(playerEntity, PlayerDto.class);
    }

    @Override
    public PlayerEntity mapFrom(PlayerDto playerDto) {
        return modelMapper.map(playerDto, PlayerEntity.class);
    }
}