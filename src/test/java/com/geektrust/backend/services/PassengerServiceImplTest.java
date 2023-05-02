package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.PassengerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {
    private Passenger passenger;
    private MetroCard metroCard;

    @Mock
    private StationService stationServiceMock;

    @Mock
    private MetroCardService metroCardServiceMock;

    @Mock
    private MetroCardRepository metroCardRepositoryMock;

    @Mock
    private PassengerRepository passengerRepositoryMock;

    @InjectMocks
    private PassengerServiceImpl passengerServiceImpl;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
        passenger = new Passenger(metroCard, PassengerType.ADULT, "AIRPORT");
    }

    @Test
    @DisplayName("create method should create and return new Passenger if it's not present in database")
    public void create_shouldReturnNewPassenger_IfNotPresent() {
        //Arrange
        Passenger expectedPassenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(metroCard));
        when(passengerRepositoryMock.findByMetroCard(metroCard)).thenReturn(Optional.empty());
        when(passengerRepositoryMock.save(passenger)).thenReturn(expectedPassenger);

        //Act
        Passenger actualPassenger = passengerServiceImpl.create("MC1", PassengerType.ADULT, "AIRPORT");

        //Assert
        Assertions.assertEquals(expectedPassenger, actualPassenger);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(passengerRepositoryMock, times(1)).findByMetroCard(metroCard);
        verify(passengerRepositoryMock, times(1)).save(passenger);
    }

    @Test
    @DisplayName("create method should return the existing Passenger by updating the boarding station if it's already present in database")
    public void create_shouldReturnExistingPassenger_IfAlreadyPresent() {
        //Arrange
        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Passenger expectedPassenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(metroCard));
        when(passengerRepositoryMock.findByMetroCard(metroCard)).thenReturn(Optional.ofNullable(passenger1));
        
        //Act
        Passenger actualPassenger = passengerServiceImpl.create("MC1", PassengerType.ADULT, "CENTRAL");

        //Assert
        Assertions.assertEquals(expectedPassenger, actualPassenger);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(passengerRepositoryMock, times(1)).findByMetroCard(metroCard);
        verify(passengerRepositoryMock, times(0)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("create method should throw MetroCardNotFoundException if the MetroCard is not found")
    public void create_shouldThrowMetroCardNotFoundException() {
        //Arrange
        when(metroCardRepositoryMock.findByCardNumber("MC3")).thenReturn(Optional.empty());

        //Act and Assert
        Assertions.assertThrows(MetroCardNotFoundException.class, () -> passengerServiceImpl.create("MC3", PassengerType.ADULT, "CENTRAL"));
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC3");
        verify(passengerRepositoryMock, times(0)).findByMetroCard(any(MetroCard.class));
        verify(passengerRepositoryMock, times(0)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("travel method should get the travel charge of the journey and make the payment if there is sufficient balance in the MetroCard and add the Passenger to the boarded list of station")
    public void travel_makePayment_IfSufficientBalance() {
        //Arrange
        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.KID, "AIRPORT");
        when(stationServiceMock.getTravelCharge(passenger1)).thenReturn(50);

        //Act
        passengerServiceImpl.travel(passenger1);

        //Assert
        verify(stationServiceMock, times(1)).getTravelCharge(passenger1);
        verify(metroCardServiceMock, times(1)).makePayment(metroCard, 50, passenger1);
        verify(metroCardServiceMock, times(0)).recharge(any(MetroCard.class), anyInt(), any(Passenger.class));
        verify(stationServiceMock, times(1)).addPassengerToBoardedList(passenger1);
    }

    @Test
    @DisplayName("travel method should get the travel charge of the journey and make the payment after recharging the MetroCard if there is no sufficient balance in MetroCard and add the Passenger to the boarded list of station")
    public void travel_rechargeMetroCardAndMakePayment_IfNoSufficientBalance() {
        //Arrange
        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        when(stationServiceMock.getTravelCharge(passenger1)).thenReturn(200);

        //Act
        passengerServiceImpl.travel(passenger1);

        //Assert
        verify(stationServiceMock, times(1)).getTravelCharge(passenger1);
        verify(metroCardServiceMock, times(1)).recharge(metroCard, 200, passenger1);
        verify(metroCardServiceMock, times(1)).makePayment(metroCard, 200, passenger1);
        verify(stationServiceMock, times(1)).addPassengerToBoardedList(passenger1);
    }
}
