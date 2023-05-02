package com.geektrust.backend.services;

import java.util.List;
import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.Station;

public interface StationService {
    public Station create(String stationName);
    public void addPassengerToBoardedList(Passenger passenger);
    public void collectTravelCharge(Passenger passenger, int travelCharge);
    public void collectServiceFee(Passenger passenger, int rechargeAmount);
    public List<Station> getAllStations();
    public int getTravelCharge(Passenger passenger);
    public CollectionSummary getCollectionSummary(Station station);
    public PassengerSummary getPassengerSummary(Station station);
}
