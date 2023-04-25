package com.geektrust.backend.commands;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.services.MetroCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BalanceCommandTest {
   
    @Mock
    private MetroCardService metroCardServiceMock;

    @InjectMocks
    private BalanceCommand balanceCommand;

    @Test
    @DisplayName("execute method of BalanceCommand should create a new MetroCard given cardNumber and balance")
    public void execute_shouldCreateMetroCard_givenCardNumberAndBalance() {
        //Arrange
        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        List<String> tokens = new ArrayList<>(Arrays.asList("BALANCE", "MC1", "600"));
        when(metroCardServiceMock.create("MC1", 600)).thenReturn(metroCard);

        //Act
        balanceCommand.execute(tokens);

        //Assert
        verify(metroCardServiceMock, times(1)).create("MC1", 600);
    }
}
