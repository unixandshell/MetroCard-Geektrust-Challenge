package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PassengerRepositoryImplTest {
    private PassengerRepository passengerRepository;

    @BeforeEach
    public void setup() {
        Map<String, Passenger> passengerMap = new HashMap<>();
        MetroCard metroCard1 = new MetroCard("1", "MC1", 100);
        MetroCard metroCard2 = new MetroCard("2", "MC2", 200);

        passengerMap.put("1", new Passenger("1", metroCard1, PassengerType.ADULT, "CENTRAL"));
        passengerMap.put("2", new Passenger("2", metroCard2, PassengerType.KID, "AIRPORT"));

        passengerRepository = new PassengerRepositoryImpl(passengerMap);
    }

    @Test
    @DisplayName("save method should create and return new Passenger")
    public void savePassenger() {
        //Arrange
        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Passenger passenger3 = new Passenger(metroCard3, PassengerType.ADULT, "CENTRAL");
        Passenger expectedPassenger = new Passenger("3", metroCard3, PassengerType.ADULT, "CENTRAL");

        //Act
        Passenger actualPassenger = passengerRepository.save(passenger3);

        //Assert
        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    @DisplayName("findByMetroCard method should return Passenger given metroCard")
    public void findByMetroCard_shouldReturnPassenger_givenMetroCard() {
        //Arrange
        MetroCard metroCard2 = new MetroCard("2", "MC2", 200);
        Passenger expectedPassenger = new Passenger("2", metroCard2, PassengerType.KID, "AIRPORT");

        //Act
        Passenger actualPassenger = passengerRepository.findByMetroCard(metroCard2).get();

        //Assert
        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    @DisplayName("findByMetroCard method should return empty if the given metroCard is not found")
    public void findByMetroCard_shouldReturnEmpty_IfMetroCardNotFound() {
        //Arrange
        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Optional<Passenger> expectedPassenger = Optional.empty();

        //Act
        Optional<Passenger> actualPassenger = passengerRepository.findByMetroCard(metroCard3);

        //Assert
        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }
}
