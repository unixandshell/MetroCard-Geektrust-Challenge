package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationTest {
    @Test
    @DisplayName("addTravelCharge method should add the given travel charge to the travel charge collection of station")
    public void addTravelCharge_shouldAddTravelCharge_toTravelChargeCollection() {
        //Arrange 
        int expectedTravelChargeCollection = 200;
        Station station = new Station("1", "AIRPORT");

        //Act
        station.addTravelCharge(200);
        int actualTravelChargeCollection = station.getTravelChargeCollection();

        //Assert
        Assertions.assertEquals(expectedTravelChargeCollection, actualTravelChargeCollection);
    }

    @Test
    @DisplayName("addServiceFee method should add the given service fee to the service fee collection of station")
    public void addServiceFee_shouldAddServiceFee_toServiceFeeCollection() {
        //Arrange 
        int expectedServiceFeeCollection = 100;
        Station station = new Station("1", "AIRPORT");

        //Act
        station.addServiceFee(100);
        int actualServiceFeeCollection = station.getServiceFeeCollection();

        //Assert
        Assertions.assertEquals(expectedServiceFeeCollection, actualServiceFeeCollection);
    }

    @Test
    @DisplayName("addDiscount method should add the given discount to the discount collection of station")
    public void addDiscount_shouldAddDiscount_toDiscountCollection() {
        //Arrange 
        int expectedDiscountCollection = 300;
        Station station = new Station("1", "AIRPORT");

        //Act
        station.addDiscount(300);
        int actualDiscountCollection = station.getDiscountCollection();

        //Assert
        Assertions.assertEquals(expectedDiscountCollection, actualDiscountCollection);
    }

    @Test
    @DisplayName("addPassenger method should add the given passenger to the boardedPassengers list of station")
    public void addPassenger_shouldAddPassenger() {
        //Arrange
        int expectedCount = 1;
        MetroCard metroCard = new MetroCard("1", "MC1", 100);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.KID, "CENTRAL");
        Station station = new Station("1", "AIRPORT");

        //Act
        station.addPassenger(passenger);
        int actualCount = station.getBoardedPassengers().size();

        //Assert
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("getTotalCollection method should return the sum of travelChargeCollection and serviceFeeCollection")
    public void getTotalCollection_shouldReturnTotalCollection() {
        //Arrange
        int expectedTotalCollection = 500;
        Station station = new Station("1", "AIRPORT");
        station.addTravelCharge(400);
        station.addServiceFee(100);

        //Act
        int actualTotalCollection = station.getTotalCollection();

        //Assert
        Assertions.assertEquals(expectedTotalCollection, actualTotalCollection);
    }
}
