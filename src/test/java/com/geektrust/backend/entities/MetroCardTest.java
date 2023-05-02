package com.geektrust.backend.entities;

import com.geektrust.backend.exceptions.InvalidAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MetroCardTest {

    @Test
    @DisplayName("addAmount method should add the given amount to the balance of MetroCard")
    public void addAmount_shouldAddAmount_toMetroCard() {
        //Arrange
        int expectedBalance = 200;
        MetroCard metroCard = new MetroCard("MC1", 100);

        //Act
        metroCard.addAmount(100);
        int actualBalance = metroCard.getBalance();

        //Assert
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    @DisplayName("deductAmount method should deduct the given amount from the balance of MetroCard")
    public void deductAmount_shouldDeductAmount_fromMetroCard() {
        //Arrange
        int expectedBalance = 100;
        MetroCard metroCard = new MetroCard("MC1", 200);

        //Act
        metroCard.deductAmount(100);
        int actualBalance = metroCard.getBalance();

        //Assert
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    @DisplayName("addAmount method should throw InvalidAmountException if the given amount is less than or equal to zero")
    public void addAmount_shouldThrowInvalidAmountException_GivenInvalidAmount() {
        //Arrange
        MetroCard metroCard = new MetroCard("MC1", 200);

        //Act and Assert
        Assertions.assertThrows(InvalidAmountException.class, () -> metroCard.addAmount(-100));
    }

    @Test
    @DisplayName("deductAmount method should throw InvalidAmountException if the given amount is less than or equal to zero OR greater than the balance of MetroCard ")
    public void deductAmount_shouldThrowInvalidAmountException_GivenInvalidAmount() {
        //Arrange
        MetroCard metroCard = new MetroCard("MC1", 200);

        //Act and Assert
        Assertions.assertThrows(InvalidAmountException.class, () -> metroCard.deductAmount(300));
    }

    @Test
    @DisplayName("hasSufficientBalance method should return true if the given amount is less than or equal to the balance of MetroCard")
    public void hasSufficientBalance_shouldReturnTrue_GivenAmount() {
        //Arrange
        MetroCard metroCard = new MetroCard("MC1", 200);

        //Act and Assert
        Assertions.assertTrue(metroCard.hasSufficientBalance(100));
    }

    @Test
    @DisplayName("hasSufficientBalance method should return false if the given amount is greater than the balance of MetroCard")
    public void hasSufficientBalance_shouldReturnFalse_GivenAmount() {
        //Arrange
        MetroCard metroCard = new MetroCard("MC1", 200);

        //Act and Assert
        Assertions.assertFalse(metroCard.hasSufficientBalance(400));
    }
}
