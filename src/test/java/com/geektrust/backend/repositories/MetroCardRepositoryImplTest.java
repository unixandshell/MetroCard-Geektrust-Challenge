package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MetroCardRepositoryImplTest {
    private MetroCardRepository metroCardRepository;
    
    @BeforeEach
    public void setup() {
        Map<String, MetroCard> metroCardMap = new HashMap<>();
        metroCardMap.put("1", new MetroCard("1", "MC1", 100));
        metroCardMap.put("2", new MetroCard("2", "MC2", 200));
        metroCardMap.put("3", new MetroCard("3", "MC3", 300));

        metroCardRepository = new MetroCardRepositoryImpl(metroCardMap);
    }

    @Test
    @DisplayName("save method should create and return new MetroCard")
    public void saveMetroCard() {
        //Arrange
        MetroCard metroCard4 = new MetroCard("MC4", 400);
        MetroCard expectedMetroCard = new MetroCard("4", "MC4", 400);

        //Act
        MetroCard actualMetroCard = metroCardRepository.save(metroCard4);

        //Assert
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }

    @Test
    @DisplayName("findByCardNumber method should return MetroCard given cardNumber")
    public void findByCardNumber_shouldReturnMetroCard_givenCardNumber() {
        //Arrange
        MetroCard expectedMetroCard = new MetroCard("2", "MC2", 200);

        //Act
        MetroCard actualMetroCard = metroCardRepository.findByCardNumber("MC2").get();

        //Assert
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }

    @Test
    @DisplayName("findByCardNumber method should return empty if the given cardNumber is not found")
    public void findByCardNumber_shouldReturnEmpty_IfCardNumberNotFound() {
        //Arrange
        Optional<MetroCard> expectedMetroCard = Optional.empty();

        //Act
        Optional<MetroCard> actualMetroCard = metroCardRepository.findByCardNumber("MC6");

        //Assert
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }
}
