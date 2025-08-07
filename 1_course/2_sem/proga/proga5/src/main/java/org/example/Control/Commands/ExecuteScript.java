package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Control.CommandManager;
import org.example.Service.TransparentScannerWrapper;

import java.util.Scanner;

/**
 * Класс `ExecuteScript` реализует интерфейс `Command` и представляет команду выполнения скрипта.
 * Команда читает команды из файла и передает их на выполнение в `CommandManager`.
 */
public class ExecuteScript implements Command {
    /**
     * Объект класса `Scanner` для чтения команд из файла.
     */
    private TransparentScannerWrapper scanner;

    /**
     * Объект класса `CommandManager`, который управляет выполнением команд.
     */
    private CommandManager manager;

    /**
     * Конструктор класса `ExecuteScript`.
     *
     * @param scanner Объект класса `Scanner` для чтения команд из файла.
     * @param manager Объект класса `CommandManager`, который управляет выполнением команд.
     */
    public ExecuteScript(TransparentScannerWrapper scanner, CommandManager manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    /**
     * Метод `execute` выполняет команду выполнения скрипта.
     * Читает команды из файла и передает их на выполнение в `CommandManager`.
     */
    @Override
    public void execute() {
        while (scanner.hasNextLine()) {
            String scn = scanner.nextLine();
            if (scn.equals("exit")){
                break;
            }
            manager.parseUserCommand(scanner, scn);
        }
        scanner.close();
    }
}