package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.MetroCardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroCardServiceImplTest {
    private Passenger passenger;
    private MetroCard metroCard;

    @Mock
    private StationService stationServiceMock;

    @Mock
    private MetroCardRepository metroCardRepositoryMock;

    @InjectMocks
    private MetroCardServiceImpl metroCardServiceImpl;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
        passenger = new Passenger("1", metroCard, PassengerType.KID, "CENTRAL");
    }

    @Test
    @DisplayName("create method should create and return new MetroCard if it's not present in database")
    public void create_shouldReturnNewMetroCard_IfNotPresent() {
        //Arrange
        MetroCard expectedMetroCard = new MetroCard("1", "MC1", 100);
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.empty());
        when(metroCardRepositoryMock.save(any(MetroCard.class))).thenReturn(expectedMetroCard);

        //Act
        MetroCard actualMetroCard = metroCardServiceImpl.create("MC1", 100);

        //Assert
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(metroCardRepositoryMock, times(1)).save(any(MetroCard.class));
    }

    @Test
    @DisplayName("create method should return the existing MetroCard if it's already present in database")
    public void create_shouldReturnExistingMetroCard_IfAlreadyPresent() {
        //Arrange 
        MetroCard expectedMetroCard = new MetroCard("1", "MC1", 100);
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(expectedMetroCard));

        //Act
        MetroCard actualMetroCard = metroCardServiceImpl.create("MC1", 100);

        //Assert
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(metroCardRepositoryMock, times(0)).save(any(MetroCard.class));
    }

    @Test
    @DisplayName("recharge method should recharge the MetroCard given travel charge")
    public void recharge_shouldRechargeMetroCard_givenTravelCharge() {
        //Arrange
        int expectedBalance = 200;

        //Act
        metroCardServiceImpl.recharge(metroCard, 200, passenger);
        int actualBalance = metroCard.getBalance();

        //Assert
        Assertions.assertEquals(expectedBalance, actualBalance);
        verify(stationServiceMock, times(1)).collectServiceFee(passenger, 100);
    }

    @Test
    @DisplayName("makePayment method should make payment given travel charge")
    public void makePayment_shouldMakePayment_givenTravelCharge() {
        //Arrange
        int expectedBalance = 0;

        //Act
        metroCardServiceImpl.makePayment(metroCard, 100, passenger);
        int actualBalance = metroCard.getBalance();

        //Assert
        Assertions.assertEquals(expectedBalance, actualBalance);
        verify(stationServiceMock, times(1)).collectTravelCharge(passenger, 100);
    }
}
