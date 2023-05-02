package com.geektrust.backend.services;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.repositories.MetroCardRepository;

public class MetroCardServiceImpl implements MetroCardService {
    private final StationService stationService;
    private final MetroCardRepository metroCardRepository;

    public MetroCardServiceImpl(StationService stationService, MetroCardRepository metroCardRepository) {
        this.stationService = stationService;
        this.metroCardRepository = metroCardRepository;
    }

    @Override
    public MetroCard create(String cardNumber, int balance) {
        Optional<MetroCard> maybeMetroCard = metroCardRepository.findByCardNumber(cardNumber);
       
        if(maybeMetroCard.isPresent()) {
            MetroCard metroCard = maybeMetroCard.get();
            return metroCard;
        }
        else {
            MetroCard metroCard = new MetroCard(cardNumber, balance);
            return metroCardRepository.save(metroCard);
        }
    }

    @Override
    public void recharge(MetroCard metroCard, int travelCharge, Passenger passenger) {
        int balance = metroCard.getBalance();
        int rechargeAmount = travelCharge - balance;
        metroCard.addAmount(rechargeAmount);
        stationService.collectServiceFee(passenger, rechargeAmount);
    }

    @Override
    public void makePayment(MetroCard metroCard, int travelCharge, Passenger passenger) {
        metroCard.deductAmount(travelCharge);
        stationService.collectTravelCharge(passenger, travelCharge);
    }  
}
