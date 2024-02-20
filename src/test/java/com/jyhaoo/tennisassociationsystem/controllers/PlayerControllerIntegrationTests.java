package com.jyhaoo.tennisassociationsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyhaoo.tennisassociationsystem.TestDataUtil;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import com.jyhaoo.tennisassociationsystem.services.PlayerService;
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
public class PlayerControllerIntegrationTests {

    private PlayerService playerService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public PlayerControllerIntegrationTests(PlayerService playerService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.playerService = playerService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    /* Http Status Codes */
    @Test
    public void testThatListPlayersReturnHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatCreatePlayerSuccessfullyReturns201Created() throws Exception {
        PlayerEntity testPlayer = TestDataUtil.createTestPlayerEntityA();
        testPlayer.setId(null);
        String playerJson = objectMapper.writeValueAsString(testPlayer);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatGetPlayersReturnsHttpStatus200WhenPlayerExist() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        playerService.save(player);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetPlayersReturnHttpStatus404WhenNoPlayerExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /* Other functionality */

    @Test
    public void testThatCreatePlayerSuccessfullyReturnsSavedPlayer() throws Exception {
        PlayerEntity testPlayer = TestDataUtil.createTestPlayerEntityA();
        testPlayer.setId(null);
        String playerJson = objectMapper.writeValueAsString(testPlayer);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testPlayer.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(testPlayer.getRating())
        );
    }

    @Test
    public void testThatListPlayersReturnListOfPlayers() throws Exception {
        PlayerEntity testPlayer = TestDataUtil.createTestPlayerEntityA();
        playerService.save(testPlayer);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testPlayer.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].rating").value(testPlayer.getRating())
        );
    }
}
