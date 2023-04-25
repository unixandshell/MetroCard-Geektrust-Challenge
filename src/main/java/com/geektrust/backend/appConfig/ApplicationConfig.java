package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.BalanceCommand;
import com.geektrust.backend.commands.CheckInCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.PrintSummaryCommand;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.MetroCardRepositoryImpl;
import com.geektrust.backend.repositories.PassengerRepository;
import com.geektrust.backend.repositories.PassengerRepositoryImpl;
import com.geektrust.backend.repositories.StationRepository;
import com.geektrust.backend.repositories.StationRepositoryImpl;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.MetroCardServiceImpl;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.PassengerServiceImpl;
import com.geektrust.backend.services.StationService;
import com.geektrust.backend.services.StationServiceImpl;

public class ApplicationConfig {
    private final MetroCardRepository metroCardRepository = new MetroCardRepositoryImpl();
    private final PassengerRepository passengerRepository = new PassengerRepositoryImpl();
    private final StationRepository stationRepository = new StationRepositoryImpl();

    private final StationService stationService = new StationServiceImpl(stationRepository);
    private final MetroCardService metroCardService = new MetroCardServiceImpl(stationService, metroCardRepository);
    private final PassengerService passengerService = new PassengerServiceImpl(stationService, metroCardService, metroCardRepository, passengerRepository);

    private final BalanceCommand balanceCommand = new BalanceCommand(metroCardService);
    private final CheckInCommand checkInCommand = new CheckInCommand(passengerService, stationService);
    private final PrintSummaryCommand printSummaryCommand = new PrintSummaryCommand(stationService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("BALANCE", balanceCommand);
        commandInvoker.register("CHECK_IN", checkInCommand);
        commandInvoker.register("PRINT_SUMMARY", printSummaryCommand);
        return commandInvoker;
    }
}
