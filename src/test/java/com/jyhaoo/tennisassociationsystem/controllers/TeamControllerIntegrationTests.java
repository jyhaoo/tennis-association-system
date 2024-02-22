package com.jyhaoo.tennisassociationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyhaoo.tennisassociationsystem.TestDataUtil;
import com.jyhaoo.tennisassociationsystem.domain.dto.TeamDto;
import com.jyhaoo.tennisassociationsystem.domain.entities.TeamEntity;
import com.jyhaoo.tennisassociationsystem.services.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TeamControllerIntegrationTests {
    private TeamService teamService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TeamControllerIntegrationTests(TeamService teamService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.teamService = teamService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    /* Http Status Codes */
    @Test
    public void testThatCreateTeamSuccessfullyReturns201Created() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamEntityA();
        team.setId(null);
        String teamString = objectMapper.writeValueAsString(team);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamString)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatGetTeamsSuccessfullyReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTeamByIdSuccessfullyReturn200IfExists() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamEntityA();
        teamService.save(team);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTeamByIdReturns400IfTeamDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPutTeamReturns200() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamEntityA();
        teamService.save(team);
        String teamString = objectMapper.writeValueAsString(team);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamString)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateExistingTeamReturnsHttpStatus200() throws Exception {
        TeamEntity teamEntity = TestDataUtil.createTestTeamEntityA();
        TeamEntity savedTeam = teamService.save(teamEntity);

        TeamDto teamDto = TestDataUtil.createTestTeamDto();
        teamDto.setId(savedTeam.getId());
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/teams/" + savedTeam.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingTeamReturnsHttpStatus404ForTeamDoesNotExist() throws Exception {
        TeamDto teamDto = TestDataUtil.createTestTeamDto();
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/teams/" + teamDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /* Create & Read */
    @Test
    public void testThatGetOneTeamReturnsTeam() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamEntityA();
        teamService.save(team);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/" + team.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(team.getName())
        );
    }

    @Test
    public void testThatGetTeamsReturnsListOfTeams() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamEntityA();
        teamService.save(team);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(team.getName())
        );
    }

    @Test
    public void testThatPutTeamReturnsUpdatedTeam() throws Exception {
        TeamEntity teamA = TestDataUtil.createTestTeamEntityA();
        teamService.save(teamA);
        TeamEntity teamB = TestDataUtil.createTestTeamEntityB();
        teamB.setId(teamA.getId());
        String teamBString = objectMapper.writeValueAsString(teamB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/teams/" + teamA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamBString)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(teamA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(teamB.getName())
        );
    }

    @Test
    public void testThatPatchUpdatesExistingTeam() throws Exception {
        TeamEntity teamEntity = TestDataUtil.createTestTeamEntityA();
        TeamEntity savedTeam = teamService.save(teamEntity);

        TeamDto teamDto = TestDataUtil.createTestTeamDto();
        teamDto.setId(savedTeam.getId());
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/teams/" + savedTeam.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(teamDto.getName())
        );
    }
}
