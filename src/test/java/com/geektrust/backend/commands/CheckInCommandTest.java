package com.geektrust.backend.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.InvalidPassengerException;
import com.geektrust.backend.exceptions.InvalidStationNameException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.StationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckInCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    private PassengerService passengerServiceMock;

    @Mock
    private StationService stationServiceMock;

    @InjectMocks 
    private CheckInCommand checkInCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of CheckInCommand should create a new Passenger, Station and deduct the appropriate amount of travel charge from the MetroCard of the passenger given cardNumber, passengerType and fromStation")
    public void execute_shouldCreatePassengerAndStation_andDeductAmountFromMetroCard() throws MetroCardNotFoundException, InvalidAmountException, StationNotFoundException, InvalidStationNameException, InvalidPassengerException {
        //Arrange
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        Station station = new Station("1", "CENTRAL");
        List<String> tokens = new ArrayList<>(Arrays.asList("CHECK_IN", "MC1", "ADULT", "CENTRAL"));
        when(passengerServiceMock.create("MC1", PassengerType.ADULT, "CENTRAL")).thenReturn(passenger);
        when(stationServiceMock.create("CENTRAL")).thenReturn(station);

        //Act
        checkInCommand.execute(tokens);

        //Assert
        verify(passengerServiceMock, times(1)).create("MC1", PassengerType.ADULT, "CENTRAL");
        verify(stationServiceMock, times(1)).create("CENTRAL");
        verify(passengerServiceMock, times(1)).travel(passenger);
    }

    @Test
    @DisplayName("execute method of CheckInCommand should print error message to console if MetroCard with given cardNumber is not found")
    public void execute_shouldPrintErrorMessage_IfMetroCardNotFound() throws InvalidAmountException, StationNotFoundException, MetroCardNotFoundException, InvalidStationNameException, InvalidPassengerException {
        //Arrange
        String expectedOutput = "MetroCard with cardNumber: MC10 not found!";
        List<String> tokens = new ArrayList<>(Arrays.asList("CHECK_IN", "MC10", "ADULT", "AIRPORT"));
        doThrow(new MetroCardNotFoundException(expectedOutput)).when(passengerServiceMock).create("MC10", PassengerType.ADULT, "AIRPORT");

        //Act
        checkInCommand.execute(tokens);

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(passengerServiceMock, times(1)).create("MC10", PassengerType.ADULT, "AIRPORT");
        verify(stationServiceMock, times(0)).create(any(String.class));
        verify(passengerServiceMock, times(0)).travel(any(Passenger.class));

    }

    @Test
    @DisplayName("execute method of CheckInCommand should print error message to console if Station with given name is not found")
    public void execute_shouldPrintErrorMessage_IfStationNotFound() throws InvalidAmountException, StationNotFoundException, MetroCardNotFoundException, InvalidStationNameException, InvalidPassengerException {
        //Arrange
        MetroCard metroCard = new MetroCard("2", "MC2", 200);
        Passenger passenger = new Passenger("2", metroCard, PassengerType.KID, "RANDOM_NAME");
        String expectedOutput = "Station with name: RANDOM_NAME not found!";
        List<String> tokens = new ArrayList<>(Arrays.asList("CHECK_IN", "MC2", "KID", "RANDOM_NAME"));
        when(passengerServiceMock.create("MC2", PassengerType.KID, "RANDOM_NAME")).thenReturn(passenger);
        doThrow(new StationNotFoundException(expectedOutput)).when(passengerServiceMock).travel(passenger);

        //Act
        checkInCommand.execute(tokens);

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(passengerServiceMock, times(1)).create("MC2", PassengerType.KID, "RANDOM_NAME");
        verify(stationServiceMock, times(1)).create("RANDOM_NAME");
        verify(passengerServiceMock, times(1)).travel(passenger);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
