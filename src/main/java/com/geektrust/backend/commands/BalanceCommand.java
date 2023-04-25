package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.MetroCardService;

public class BalanceCommand implements ICommand {
    private final MetroCardService metroCardService;

    public BalanceCommand(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        String cardNumber = tokens.get(1);
        int balance = Integer.valueOf(tokens.get(2));
        metroCardService.create(cardNumber, balance);
    } 
}
