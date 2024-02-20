package com.jyhaoo.tennisassociationsystem.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq")
    private Long id;

    private String name;

    private List<PlayerEntity> teamMembers;
}