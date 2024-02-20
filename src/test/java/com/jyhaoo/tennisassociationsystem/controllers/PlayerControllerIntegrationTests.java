package com.jyhaoo.tennisassociationsystem.controllers;

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
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        player.setId(null);
        String playerJson = objectMapper.writeValueAsString(player);

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

    @Test
    public void testThatFullyUpdatePlayerReturnHttpStatus200() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        playerService.save(player);
        String playerString = objectMapper.writeValueAsString(player);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerString)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdatePlayerReturnsStatus200() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        playerService.save(player);

        player.setName("Tommy Paul");
        String playerString = objectMapper.writeValueAsString(player);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + player.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerString)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdatePlayerReturnsStatus400ForNonExistingPlayer() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        String playerString = objectMapper.writeValueAsString(player);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerString)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeletePlayerReturnsHttpStatus204() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /* Create & Read */

    @Test
    public void testThatCreatePlayerSuccessfullyReturnsSavedPlayer() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        player.setId(null);
        String playerJson = objectMapper.writeValueAsString(player);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(player.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(player.getRating())
        );
    }

    @Test
    public void testThatListPlayersReturnListOfPlayers() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        playerService.save(player);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(player.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].rating").value(player.getRating())
        );
    }

    /* Modify */
    @Test
    public void testThatFullyUpdatesExistingPlayer() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        PlayerEntity savedPlayer = playerService.save(player);

        PlayerEntity putPlayer = TestDataUtil.createTestPlayerEntityB();
        putPlayer.setId(savedPlayer.getId());

        String putPlayerString = objectMapper.writeValueAsString(putPlayer);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/" + savedPlayer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putPlayerString)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPlayer.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(putPlayer.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(putPlayer.getRating())
        );
    }

    @Test
    public void testThatPartialUpdatePlayerReturnsUpdatedPlayer() throws Exception {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        PlayerEntity savedPlayer = playerService.save(player);

        player.setName("Tommy Paul");
        String playerString = objectMapper.writeValueAsString(player);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + savedPlayer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerString)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPlayer.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(player.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(player.getRating())
        );
    }
}
