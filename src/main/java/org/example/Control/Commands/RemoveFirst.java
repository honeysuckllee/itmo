package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;

/**
 * Класс `RemoveFirst` реализует интерфейс `Command` и представляет команду удаления первого элемента из коллекции.
 * При выполнении команды удаляется первый элемент коллекции.
 */
public class RemoveFirst implements Command {
    /**
     * Коллекция `Deque`, из которой удаляется первый элемент.
     */
    private Deque deque;

    /**
     * Конструктор класса `RemoveFirst`.
     *
     * @param deque Коллекция `Deque`, из которой удаляется первый элемент.
     */
    public RemoveFirst(Deque deque) {
        this.deque = deque;
    }

    /**
     * Метод `execute` удаляет первый элемент из коллекции.
     */
    @Override
    public void execute() {
        deque.removeFirst();
    }
}