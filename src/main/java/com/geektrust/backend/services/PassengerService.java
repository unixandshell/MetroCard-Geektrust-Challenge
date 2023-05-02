package com.geektrust.backend.services;

import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;

public interface PassengerService {
    public Passenger create(String cardNumber, PassengerType passengerType, String boardingStation);
    public void travel(Passenger passenger);
}
