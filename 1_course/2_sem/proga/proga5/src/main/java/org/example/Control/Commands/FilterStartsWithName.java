package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;
import org.example.Service.TransparentScannerWrapper;

import java.util.Scanner;

import static org.example.Service.Utilites.getValidName;

/**
 * Класс `FilterStartsWithName` реализует интерфейс `Command` и представляет команду фильтрации элементов коллекции,
 * которые начинаются с указанного имени.
 */
public class FilterStartsWithName implements Command {
    /**
     * Объект класса `Scanner` для чтения ввода пользователя.
     */
    private TransparentScannerWrapper scanner;

    /**
     * Коллекция `Deque`, с которой работает команда.
     */
    private Deque deque;

    /**
     * Имя, с которого должны начинаться элементы коллекции.
     */
    private String name;

    /**
     * Конструктор класса `FilterStartsWithName`.
     *
     * @param scanner Объект класса `Scanner` для чтения ввода пользователя.
     * @param deque Коллекция `Deque`, с которой работает команда.
     * @param name Имя, с которого должны начинаться элементы коллекции.
     */
    public FilterStartsWithName(TransparentScannerWrapper scanner, Deque deque, String name) {
        this.scanner = scanner;
        this.deque = deque;
        this.name = name;
    }

    /**
     * Метод `execute` выполняет команду фильтрации элементов коллекции, которые начинаются с указанного имени.
     * Если имя не было передано, запрашивает его у пользователя.
     */
    @Override
    public void execute() {
        if (name.isEmpty()) {
            name = getValidName(scanner);
        }
        deque.filterStartsWithName(name);
    }
}