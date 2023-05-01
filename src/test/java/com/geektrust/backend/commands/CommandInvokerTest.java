package com.geektrust.backend.commands;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import com.geektrust.backend.exceptions.NoSuchCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandInvokerTest {
    
    private CommandInvoker commandInvoker;

    @Mock
    private BalanceCommand balanceCommand;

    @Mock 
    private CheckInCommand checkInCommand;

    @Mock
    private PrintSummaryCommand printSummaryCommand;

    @BeforeEach
    public void setup() {
        commandInvoker = new CommandInvoker();
        commandInvoker.register("BALANCE", balanceCommand);
        commandInvoker.register("CHECK_IN", checkInCommand);
        commandInvoker.register("PRINT_SUMMARY", printSummaryCommand);
    }

    @Test
    @DisplayName("executeCommand method Should Execute Command Given CommandName and List of tokens")
    public void executeCommand_shouldExecuteCommand_givenNameAndTokens() throws NoSuchCommandException {
        //Act
        commandInvoker.executeCommand("BALANCE", new ArrayList<String>());
        commandInvoker.executeCommand("CHECK_IN", new ArrayList<String>());
        commandInvoker.executeCommand("PRINT_SUMMARY", new ArrayList<String>());

        //Assert
        verify(balanceCommand, times(1)).execute(anyList());
        verify(checkInCommand, times(1)).execute(anyList());
        verify(printSummaryCommand, times(1)).execute(anyList());
    }

    @Test
    @DisplayName("executeCommand method Should Throw NoSuchCommandException Given Incorrect CommandName and List of tokens")
    public void executeCommand_shouldThrowException_givenIncorrectNameAndTokens() {
        //Act and Assert
        Assertions.assertThrows(NoSuchCommandException.class, () -> commandInvoker.executeCommand("RANDOM_COMMAND", new ArrayList<String>()));
    }
}
