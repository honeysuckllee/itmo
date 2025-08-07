package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;
import org.example.Service.TransparentScannerWrapper;

import java.util.Scanner;

import static org.example.Service.Utilites.getValidInt;

/**
 * Класс `RemoveById` реализует интерфейс `Command` и представляет команду удаления элемента из коллекции по его идентификатору.
 * Если идентификатор не был передан, он запрашивается у пользователя.
 */
public class RemoveById implements Command {
    /**
     * Коллекция `Deque`, из которой удаляется элемент.
     */
    private Deque deque;

    /**
     * Объект класса `Scanner` для чтения ввода пользователя.
     */
    private TransparentScannerWrapper scanner;

    /**
     * Идентификатор элемента, который необходимо удалить.
     */
    private int id;

    /**
     * Конструктор класса `RemoveById`.
     *
     * @param scanner Объект класса `Scanner` для чтения ввода пользователя.
     * @param deque Коллекция `Deque`, из которой удаляется элемент.
     * @param id Идентификатор элемента, который необходимо удалить.
     */
    public RemoveById(TransparentScannerWrapper scanner, Deque deque, int id) {
        this.deque = deque;
        this.scanner = scanner;
        this.id = id;
    }

    /**
     * Метод `execute` удаляет элемент из коллекции по его идентификатору.
     * Если идентификатор не был передан, он запрашивается у пользователя.
     */
    @Override
    public void execute() {
        if (id == 0) {
            id = getValidInt(scanner, "Введите id:");
        }
        deque.removeById(id);
    }
}