package com.jyhaoo.tennisassociationsystem.mappers.impl;

import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamMapperImpl implements Mapper<TeamEntity, TeamDto> {

    private final ModelMapper modelMapper;

    public TeamMapperImpl(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    @Override
    public TeamDto mapTo(TeamEntity teamEntity) {return modelMapper.map(teamEntity, TeamDto.class);}

    @Override
    public TeamEntity mapFrom(TeamDto teamDto) {return modelMapper.map(teamDto, TeamEntity.class);}
}
