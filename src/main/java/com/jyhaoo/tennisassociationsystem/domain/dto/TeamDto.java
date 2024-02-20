package com.jyhaoo.tennisassociationsystem.domain.dto;

import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {
    private Long id;

    private String name;

//    private List<PlayerEntity> teamMembers;
}
