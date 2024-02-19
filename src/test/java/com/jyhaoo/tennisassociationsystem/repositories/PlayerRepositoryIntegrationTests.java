package com.jyhaoo.tennisassociationsystem.repositories;

import com.jyhaoo.tennisassociationsystem.TestDataUtil;
import com.jyhaoo.tennisassociationsystem.domain.entities.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerRepositoryIntegrationTests {

    private PlayerRepository underTest;

    @Autowired
    public PlayerRepositoryIntegrationTests(PlayerRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatPlayerCanBeCreatedAndRecalled() {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        underTest.save(player);

        Optional<PlayerEntity> result = underTest.findById(player.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(player);
    }

    @Test
    public void testThatMultiplePlayersCanBeCreatedAndRecalled() {
        PlayerEntity playerA = TestDataUtil.createTestPlayerEntityA();
        underTest.save(playerA);
        PlayerEntity playerB = TestDataUtil.createTestPlayerEntityB();
        underTest.save(playerB);
        PlayerEntity playerC = TestDataUtil.createTestPlayerEntityC();
        underTest.save(playerC);

        Iterable<PlayerEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(playerA, playerB, playerC);
    }

    @Test
    public void testThatPlayerCanBeUpdated() {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        underTest.save(player);

        player.setName("Tommy Paul");
        underTest.save(player);

        Optional<PlayerEntity> result = underTest.findById(player.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(player);
    }

    @Test
    public void testThatPlayerCanBeDeleted() {
        PlayerEntity player = TestDataUtil.createTestPlayerEntityA();
        underTest.save(player);
        underTest.deleteById(player.getId());
        Optional<PlayerEntity> result = underTest.findById(player.getId());
        assertThat(result).isEmpty();
    }
}
