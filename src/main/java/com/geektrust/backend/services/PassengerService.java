package com.geektrust.backend.services;

import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.exceptions.StationNotFoundException;

public interface PassengerService {
    public Passenger create(String cardNumber, PassengerType passengerType, String boardingStation) throws MetroCardNotFoundException;
    public void travel(Passenger passenger) throws InvalidAmountException, StationNotFoundException;
}
