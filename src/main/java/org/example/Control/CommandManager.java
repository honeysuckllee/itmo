package org.example.Control;

import org.example.Control.Commands.*;
import org.example.Model.Deque;
import org.example.Service.TransparentScannerWrapper;
import org.example.Service.Utilites;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;


import static org.example.Service.Utilites.integerConverter;

/**
 * Класс `CommandManager` управляет обработкой команд пользователя. Он отвечает за парсинг команд,
 * вызов соответствующих команд и выполнение скриптов.
 */
public class CommandManager {
    /**
     * Коллекция `Deque`, с которой работают команды.
     */
    private Deque deque;
    /**
     * Введенная пользователем команда.
     */
    private String inputCommand;
    /**
     * Массив, содержащий команду и её аргументы, полученные после парсинга.
     */
    private String[] finalUserCommand;
    /**
     * Объект класса `CommandInvoker`, отвечающий за вызов команд.
     */
    private CommandInvoker commandInvoker;

    /**
     * Конструктор класса `CommandManager`.
     *
     * @param deque Коллекция `Deque`, с которой будут работать команды.
     */
    public CommandManager(Deque deque) {
        this.deque = deque;
        this.commandInvoker = new CommandInvoker();
    }

    /**
     * Парсит введенную пользователем команду и вызывает соответствующую команду.
     *
     * @param scanner Объект класса `Scanner` для чтения ввода пользователя.
     * @param command Введенная пользователем команда.
     */
    public void parseUserCommand(TransparentScannerWrapper scanner, String command){
        finalUserCommand = command.trim().split(" ", 3);
        switch (finalUserCommand[0]) {
            case "":
                break;
            case "exit":
                break;
            case "info":
                commandInvoker.executeCommand(new Info(deque));
                break;
            case "help":
                commandInvoker.executeCommand(new Help());
                break;
            case "show":
                commandInvoker.executeCommand(new Show(Deque.routes));
                break;
            case "add":
                String name = "";
                if (finalUserCommand.length > 1){
                    name = finalUserCommand[1];
                }
                commandInvoker.executeCommand(new Add(scanner, deque, name));
                break;
            case "clear":
                commandInvoker.executeCommand(new Clear(deque));
                break;
            case "save":
                commandInvoker.executeCommand(new Save(deque));
                break;
            case "update":
                int id = 0;
                if (finalUserCommand.length > 1){
                    id = integerConverter(finalUserCommand[1]);
                }
                commandInvoker.executeCommand(new Update(scanner, deque, id));
                break;
            case "remove_first":
                commandInvoker.executeCommand(new RemoveFirst(deque));
                break;
            case "add_if_max":
                id = 0;
                if (finalUserCommand.length > 1){
                    id = integerConverter(finalUserCommand[1]);
                }
                commandInvoker.executeCommand(new AddIfMax(scanner, deque, id));
                break;
            case "remove_lower":
                id = 0;
                if (finalUserCommand.length > 1){
                    id = integerConverter(finalUserCommand[1]);
                }
                commandInvoker.executeCommand(new RemoveLower(scanner, deque, id));
                break;
            case "remove_by_id":
                id = 0;
                if (finalUserCommand.length > 1){
                    id = integerConverter(finalUserCommand[1]);
                }
                commandInvoker.executeCommand(new RemoveById(scanner, deque, id));
                break;
            case "filter_starts_with_name":
                name = "";
                if (finalUserCommand.length > 1){
                    name = finalUserCommand[1];
                }
                commandInvoker.executeCommand(new FilterStartsWithName(scanner, deque, name));
                break;
            case "print_unique_distance":
                commandInvoker.executeCommand(new PrintUniqueDistance(deque));
                break;
            case "print_field_descending_distance":
                commandInvoker.executeCommand(new PrintFieldDescendingDistance(deque));
                break;
            case "execute_script":
                String fileName = "";
                String scriptsPath = System.getenv("ROUTE_SCRIPTS");
                if (finalUserCommand.length > 1){
                    fileName = finalUserCommand[1];
                }
                fileName = scriptsPath + fileName;
                try {
                    File file = new File(fileName);
                    TransparentScannerWrapper scannerFile = new TransparentScannerWrapper(file, false);
                    commandInvoker.executeCommand(new ExecuteScript(scannerFile, this));
                } catch  (FileNotFoundException e) {
                    System.err.println("Неправильный путь или формат файла.");
                    break;
                }
                break;
            default:
                System.out.println("Неопознанная команда. Наберите 'help' для справки.");
        }

    }

    /**
     * Запускает основной цикл программы, в котором происходит чтение и обработка команд пользователя.
     */
    public void run(){
        inputCommand = " ";

        try {
            while (!inputCommand.equals("exit")) {
                System.out.print("Введите команду: ");
                inputCommand = Utilites.scanner().nextLine();
                parseUserCommand(Utilites.scanner(), inputCommand);
            }
        }catch (NoSuchElementException e) {
            System.out.println("Вы вышли из консольного приложения.");
        }
    }
}
