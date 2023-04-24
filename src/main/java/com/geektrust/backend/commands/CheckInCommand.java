package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.entities.Passenger;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.StationService;

public class CheckInCommand implements ICommand {
    private PassengerService passengerService;
    private StationService stationService;

    public CheckInCommand(PassengerService passengerService, StationService stationService) {
        this.passengerService = passengerService;
        this.stationService = stationService;
    }

    @Override
    public void execute(List<String> tokens) {
        String cardNumber = tokens.get(1);
        PassengerType passengerType = PassengerType.valueOf(tokens.get(2)) ;
        String stationName = tokens.get(3);

        try {
            Passenger passenger = passengerService.create(cardNumber, passengerType, stationName);
            stationService.create(stationName);
            passengerService.travel(passenger);
        } 
        catch (MetroCardNotFoundException | InvalidAmountException | StationNotFoundException e) {
            e.printStackTrace();
        } 
    }
    
}
