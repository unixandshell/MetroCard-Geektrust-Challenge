package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PassengerTest {
    private MetroCard metroCard;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
    }

    @Test
    @DisplayName("setBoardingStation method should set the boardingStation to the given value")
    public void setBoardingStation() {
        //Arrange
        String expectedBoardingStation = "AIRPORT";
        Passenger passenger = new Passenger(metroCard, PassengerType.ADULT, "CENTRAL");

        //Act
        passenger.setBoardingStation("AIRPORT");
        String actualBoardingStation = passenger.getBoardingStation();

        //Assert
        Assertions.assertEquals(expectedBoardingStation, actualBoardingStation);
    }

    @Test
    @DisplayName("updateJourneyTypeCode method should update the journeyTypeCode to 0 when called for the first time")
    public void updateJourneyTypeCode_shouldUpdateJourneyTypeCodeToZero_forFirstCall() {
        //Arrange
        int expectedJourneyTypeCode = 0;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");

        //Act
        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();

        //Assert
        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }

    @Test
    @DisplayName("updateJourneyTypeCode method should update the journeyTypeCode to 1 when called for the second time")
    public void updateJourneyTypeCode_shouldUpdateJourneyTypeCodeToOne_forSecondCall() {
        //Arrange
        int expectedJourneyTypeCode = 1;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");

        //Act
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();

        //Assert
        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }

    @Test
    @DisplayName("updateJourneyTypeCode method should update the journeyTypeCode to 0 when called for the third time")
    public void updateJourneyTypeCode_shouldUpdateJourneyTypeCodeToZero_forThirdCall() {
        //Arrange
        int expectedJourneyTypeCode = 0;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");

        //Act
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();

        //Assert
        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }  
}
