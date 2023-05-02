package com.geektrust.backend.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.dtos.PassengerTypeCount;
import com.geektrust.backend.entities.JourneyType;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.entities.TravelCharge;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.repositories.StationRepository;

public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station create(String stationName) {
        Optional<Station> maybeStation = stationRepository.findByName(stationName);

        if(maybeStation.isPresent()) {
            Station station = maybeStation.get();
            return station;
        }
        else {
            Station station = new Station(stationName);
            return stationRepository.save(station);
        }
    }

    @Override
    public void addPassengerToBoardedList(Passenger passenger) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(() -> new StationNotFoundException());
        station.addPassenger(new Passenger(passenger));
    }

    @Override
    public void collectTravelCharge(Passenger passenger, int travelCharge) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(() -> new StationNotFoundException());
        station.addTravelCharge(travelCharge);
    }

    @Override
    public void collectServiceFee(Passenger passenger, int rechargeAmount) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(() -> new StationNotFoundException());
        int serviceFee = calculateServiceFee(rechargeAmount);
        station.addServiceFee(serviceFee);
    }

    private int calculateServiceFee(int rechargeAmount) {
        final double SERVICE_FEE_RATE = 0.02;
        int serviceFee = (int)(SERVICE_FEE_RATE * rechargeAmount);
        return serviceFee;
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        Collections.sort(stations);
        return stations;
    }

    @Override
    public int getTravelCharge(Passenger passenger) {
        String passengerType = passenger.getPassengerType().toString();
        int travelCharge = TravelCharge.valueOf(passengerType).getCharge();

        int journeyTypeCode = passenger.getJourneyTypeCode();
        JourneyType journeyType = getJourneyType(journeyTypeCode);

        if(journeyType.equals(JourneyType.SINGLE))
            return travelCharge;
        else
        {
            int revisedTravelCharge = getRevisedTravelCharge(travelCharge);
            int discount = travelCharge - revisedTravelCharge;
            collectDiscount(passenger, discount);
            return revisedTravelCharge;
        }
    }

    @Override
    public CollectionSummary getCollectionSummary(Station station) {
        int totalCollection = station.getTotalCollection();
        int discountCollection = station.getDiscountCollection();
        CollectionSummary collectionSummary = new CollectionSummary(station.getName(), totalCollection, discountCollection);
        return collectionSummary;
    }

    @Override
    public PassengerSummary getPassengerSummary(Station station) {
        List<Passenger> passengers = station.getBoardedPassengers();
        Map<PassengerType, Integer> passengerTypeCountMap = getCountPassengerTypeWise(passengers);
        List<PassengerTypeCount> passengerTypeCounts = getPassengerSummaryHelper(passengerTypeCountMap);
        PassengerSummary passengerSummary = new PassengerSummary(passengerTypeCounts);
        return passengerSummary;
    } 

    private List<PassengerTypeCount> getPassengerSummaryHelper(Map<PassengerType, Integer> passengerTypeCountMap) {
        List<PassengerTypeCount> passengerTypeCounts = passengerTypeCountMap.entrySet().stream().map(entry -> new PassengerTypeCount(entry.getKey(), entry.getValue())).sorted().collect(Collectors.toList());
        return passengerTypeCounts;
    }
    
    private Map<PassengerType, Integer> getCountPassengerTypeWise(List<Passenger> passengers) {
        Map<PassengerType, Integer> passengerTypeCountMap = new HashMap<>();
        final int DEFAULT_COUNT = 0;

        for(Passenger passenger : passengers)
        {
            PassengerType passengerType = passenger.getPassengerType();
            int count = passengerTypeCountMap.getOrDefault(passengerType, DEFAULT_COUNT);
            count++;
            passengerTypeCountMap.put(passengerType, count);
        }
        return passengerTypeCountMap;
    }

    private JourneyType getJourneyType(int journeyTypeCode) {
        switch(journeyTypeCode)
        {
            case 0: return JourneyType.SINGLE;
            default: return JourneyType.RETURN;
        }
    }

    private int getRevisedTravelCharge(int travelCharge) {
        final double DISCOUNT_RATE = 0.5;
        int revisedTravelCharge = (int)(DISCOUNT_RATE * travelCharge);
        return revisedTravelCharge;
    }

    private void collectDiscount(Passenger passenger, int discount) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(() -> new StationNotFoundException());
        station.addDiscount(discount);
    }
}
