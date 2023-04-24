package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.StationNotFoundException;

public interface MetroCardService {
    public MetroCard create(String cardNumber, int balance);
    public void recharge(MetroCard metroCard, int travelCharge, Passenger passenger) throws InvalidAmountException, StationNotFoundException;
    public void makePayment(MetroCard metroCard, int travelCharge, Passenger passenger) throws InvalidAmountException, StationNotFoundException;
}
