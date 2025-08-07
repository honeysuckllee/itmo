package ru.lab6;

import lombok.Data;
import ru.lab6.Requests.Request;

import java.util.Scanner;


@Data
public class CommandHandler {
    private String command;
    private String[] commandSplit;
    public Scanner scanner;
    private String arg;
    private boolean isScript;


    public CommandHandler() {
    }

    /**
     * Launches the command processor and enters the interactive mode.
     * @return 0 if the program is finished properly.
     */
    public void readCommand() {
        String inputStr = scanner.nextLine();
        commandSplit = inputStr.trim().split(" ");
        command = commandSplit[0];
        arg = (commandSplit.length > 1) ? commandSplit[1]:""; //укороченный if
    }
    public Request generateRequest(String command, String arg, boolean isScript) {
        if (command.isEmpty() & arg.isEmpty()){
            return new Request(null,null, false);
        }
        this.command = command;
        this.arg = arg;
        this.isScript = isScript;
        return new Request(command, arg, isScript);
    }
}
