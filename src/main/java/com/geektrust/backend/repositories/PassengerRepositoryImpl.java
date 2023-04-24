package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;

public class PassengerRepositoryImpl implements PassengerRepository {

    private Map<String, Passenger> passengerMap;
    private int autoIncrement = 0;

    public PassengerRepositoryImpl(Map<String, Passenger> passengerMap) {
        this.passengerMap = passengerMap;
        this.autoIncrement = passengerMap.size();
    }

    public PassengerRepositoryImpl() {
        this.passengerMap = new HashMap<>();
    }

    @Override
    public Passenger save(Passenger entity) {
        if(entity.getId() == null)
        {
            autoIncrement++;
            Passenger passenger = new Passenger(Integer.toString(autoIncrement), entity.getMetroCard(), entity.getPassengerType(), entity.getBoardingStation());
            passengerMap.put(passenger.getId(), passenger);
            return passenger;
        }
        passengerMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Passenger> findByMetroCard(MetroCard metroCard) {
        List<Passenger> passengerList = passengerMap.values().stream().collect(Collectors.toList());

        for(Passenger passenger : passengerList)
        {
            if(passenger.getMetroCard().equals(metroCard))
                return Optional.ofNullable(passenger);
        }
        return Optional.ofNullable(null);
    }
}
