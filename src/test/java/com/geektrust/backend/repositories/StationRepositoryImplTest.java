package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationRepositoryImplTest {
    private StationRepository stationRepository;

    @BeforeEach
    public void setup() {
        Map<String, Station> stationMap = new HashMap<>();
        stationMap.put("1", new Station("1", "CENTRAL"));

        stationRepository = new StationRepositoryImpl(stationMap);   
    }

    @Test
    @DisplayName("save method should create and return new Station")
    public void saveStation() {
        //Arrange
        Station station2 = new Station("AIRPORT");
        Station expectedStation = new Station("2", "AIRPORT");

        //Act
        Station actualStation = stationRepository.save(station2);

        //Assert
        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    @DisplayName("findByName method should return Station given station name")
    public void findByName_shouldReturnStation_givenName() {
        //Arrange 
        Station expectedStation = new Station("1", "CENTRAL");

        //Act
        Station actualStation = stationRepository.findByName("CENTRAL").get();

        //Assert
        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    @DisplayName("findByName method should return empty if the given station name is not found")
    public void findByName_shouldReturnEmpty_IfNameNotFound() {
        //Arrange 
        Optional<Station> expectedStation = Optional.empty();

        //Act
        Optional<Station> actualStation = stationRepository.findByName("MG ROAD");

        //Assert
        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    @DisplayName("findAll method should return all Station")
    public void findAllStation() {
        //Arrange
        int expectedCount = 2;
        Station station2 = new Station("AIRPORT");
        stationRepository.save(station2);

        //Act
        int actualCount = stationRepository.findAll().size();

        //Assert
        Assertions.assertEquals(expectedCount, actualCount);
    }
}
