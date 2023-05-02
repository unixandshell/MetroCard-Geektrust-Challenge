package com.geektrust.backend.services;

import java.util.List;
import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.InvalidPassengerException;
import com.geektrust.backend.exceptions.StationNotFoundException;

public interface StationService {
    public Station create(String stationName);
    public void addPassengerToBoardedList(Passenger passenger) throws StationNotFoundException, InvalidPassengerException;
    public void collectTravelCharge(Passenger passenger, int travelCharge) throws StationNotFoundException, InvalidAmountException;
    public void collectServiceFee(Passenger passenger, int rechargeAmount) throws StationNotFoundException, InvalidAmountException;
    public List<Station> getAllStations();
    public int getTravelCharge(Passenger passenger) throws StationNotFoundException, InvalidAmountException;
    public CollectionSummary getCollectionSummary(Station station);
    public PassengerSummary getPassengerSummary(Station station);
}
