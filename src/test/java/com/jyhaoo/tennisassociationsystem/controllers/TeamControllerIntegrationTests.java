package com.jyhaoo.tennisassociationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyhaoo.tennisassociationsystem.TestDataUtil;
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
}
