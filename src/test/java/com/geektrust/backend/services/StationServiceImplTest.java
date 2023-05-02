package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.dtos.PassengerTypeCount;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.repositories.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StationServiceImplTest {
    
    @Mock
    private StationRepository stationRepositoryMock;

    @InjectMocks
    private StationServiceImpl stationServiceImpl;

    @Test
    @DisplayName("create method should create and return new Station if it's not present in database")
    public void create_shouldReturnNewStation_IfNotPresent() {
        //Arrange
        Station station = new Station("CENTRAL");
        Station expectedStation = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.empty());
        when(stationRepositoryMock.save(station)).thenReturn(expectedStation);

        //Act
        Station actualStation = stationServiceImpl.create("CENTRAL");

        //Assert
        Assertions.assertEquals(expectedStation, actualStation);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
        verify(stationRepositoryMock, times(1)).save(station);
    }

    @Test
    @DisplayName("create method should return the existing Station if it's already present in database")
    public void create_shouldReturnExistingStation_IfAlreadyPresent() {
        //Arrange
        Station expectedStation = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(expectedStation));
        
        //Act
        Station actualStation = stationServiceImpl.create("CENTRAL");

        //Assert
        Assertions.assertEquals(expectedStation, actualStation);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
        verify(stationRepositoryMock, times(0)).save(any(Station.class));
    }

    @Test
    @DisplayName("addPassengerToBoardedList method should add the given Passenger to the boardedList of station")
    public void addPassengerToBoardedList_shouldAddPassengerToBoardedList() {
        //Arrange
        int expectedCount = 1;
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Station station = new Station("1", "AIRPORT");
        when(stationRepositoryMock.findByName("AIRPORT")).thenReturn(Optional.ofNullable(station));

        //Act
        stationServiceImpl.addPassengerToBoardedList(passenger);
        int actualCount = station.getBoardedPassengers().size();

        //Assert
        Assertions.assertEquals(expectedCount, actualCount);
        verify(stationRepositoryMock, times(1)).findByName("AIRPORT");

    }

    @Test
    @DisplayName("addPassengerToBoardedList method should throw StationNotFoundException if the Station is not found")
    public void addPassengerToBoardedList_shouldThrowStationNotFoundException() {
        //Arrange
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.empty());

        //Act and Assert
        Assertions.assertThrows(StationNotFoundException.class, () -> stationServiceImpl.addPassengerToBoardedList(passenger));
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    @DisplayName("collectTravelCharge method should add the given travelCharge to the travelChargeCollection of station")
    public void collectTravelCharge_shouldAddTravelCharge_toTravelChargeCollection() {
        //Arrange
        int expectedTravelCharge = 200;
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        Station station = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(station));

        //Act
        stationServiceImpl.collectTravelCharge(passenger, 200);
        int actualTravelCharge = station.getTravelChargeCollection();

        //Assert
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    @DisplayName("collectServiceFee method should add the serviceFee to the serviceFeeCollection of station")
    public void collectServiceFee_shouldAddServiceFee_toServiceFeeCollection() {
        //Arrange
        int expectedServiceFee = 3;
        MetroCard metroCard = new MetroCard("1", "MC1", 50);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        Station station = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(station));

        //Act
        stationServiceImpl.collectServiceFee(passenger, 150);
        int actualServiceFee = station.getServiceFeeCollection();

        //Assert
        Assertions.assertEquals(expectedServiceFee, actualServiceFee);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    @DisplayName("getAllStations method should return all Stations sorted in descending order of their station names")
    public void getAllStations_shouldReturnAllStations() {
        //Arrange 
        Station station1 = new Station("1", "CENTRAL");
        Station station2 = new Station("2", "AIRPORT");
        List<Station> expectedStationList = Arrays.asList(station1, station2);
        List<Station> stations = new ArrayList<>(Arrays.asList(station2, station1));
        when(stationRepositoryMock.findAll()).thenReturn(stations);

        //Act
        List<Station> actualStationList = stationServiceImpl.getAllStations();

        //Assert
        Assertions.assertEquals(expectedStationList, actualStationList);
        verify(stationRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("getTravelCharge method should return the travelCharge for the given passenger with journeyType - SINGLE")
    public void getTravelCharge_shouldReturnTravelCharge_GivenPassenger_withJourneyTypeSingle() {
        //Arrange
        int expectedTravelCharge = 200;
        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        passenger.updateJourneyTypeCode();

        //Act
        int actualTravelCharge = stationServiceImpl.getTravelCharge(passenger);

        //Assert
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
    }

    @Test
    @DisplayName("getTravelCharge method should return the travelCharge for the given passenger with journeyType - RETURN and add the discount to the discountCollection of station")
    public void getTravelCharge_shouldReturnTravelCharge_GivenPassenger_withJourneyTypeReturn() {
        //Arrange
        int expectedTravelCharge = 100;
        int expectedDiscount = 100;
        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Station station = new Station("1", "AIRPORT");
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        when(stationRepositoryMock.findByName("AIRPORT")).thenReturn(Optional.ofNullable(station));

        //Act
        int actualTravelCharge = stationServiceImpl.getTravelCharge(passenger);
        int actualDiscount = station.getDiscountCollection();

        //Assert
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
        Assertions.assertEquals(expectedDiscount, actualDiscount);
        verify(stationRepositoryMock, times(1)).findByName("AIRPORT");
    }

    @Test
    @DisplayName("getCollectionSummary method should return the CollectionSummary of the given station")
    public void getCollectionSummary_shouldReturnCollectionSummary_givenStation() {
        //Arrange
        CollectionSummary expectedCollectionSummary = new CollectionSummary("CENTRAL", 403, 50);
        Station station = new Station("1", "CENTRAL");
        station.addTravelCharge(400);
        station.addServiceFee(3);
        station.addDiscount(50);

        //Act
        CollectionSummary actualCollectionSummary = stationServiceImpl.getCollectionSummary(station);

        //Assert
        Assertions.assertEquals(expectedCollectionSummary, actualCollectionSummary);
    }

    @Test
    @DisplayName("getPassengerSummary method should return the PassengerSummary of the given station")
    public void getPassengerSummary_shouldReturnPassengerSummary_givenStation() {
        //Arrange
        MetroCard metroCard1 = new MetroCard("1", "MC1", 600);
        Passenger passenger1 = new Passenger("1", metroCard1, PassengerType.SENIOR_CITIZEN, "CENTRAL");

        MetroCard metroCard2 = new MetroCard("2", "MC2", 500);
        Passenger passenger2 = new Passenger("2", metroCard2, PassengerType.ADULT, "CENTRAL");

        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Passenger passenger3 = new Passenger("3", metroCard3, PassengerType.KID, "CENTRAL");

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2, passenger3);
        Station station = new Station("1", "CENTRAL");

        for(Passenger passenger : passengers)
            station.addPassenger(passenger);

        PassengerTypeCount passengerTypeCount1 = new PassengerTypeCount(PassengerType.ADULT, 1);
        PassengerTypeCount passengerTypeCount2 = new PassengerTypeCount(PassengerType.KID, 1); 
        PassengerTypeCount passengerTypeCount3 = new PassengerTypeCount(PassengerType.SENIOR_CITIZEN, 1);
        List<PassengerTypeCount> passengerTypeCounts = Arrays.asList(passengerTypeCount1, passengerTypeCount2, passengerTypeCount3);
        PassengerSummary expectedPassengerSummary = new PassengerSummary(passengerTypeCounts);

        //Act
        PassengerSummary actualPassengerSummary = stationServiceImpl.getPassengerSummary(station);

        //Assert
        Assertions.assertEquals(expectedPassengerSummary, actualPassengerSummary);
    }
}
