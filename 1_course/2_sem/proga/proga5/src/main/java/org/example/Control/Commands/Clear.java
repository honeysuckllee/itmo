package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;

/**
 * Класс `Clear` реализует команду очистки коллекции `Deque`.
 */
public class Clear implements Command {
    /**
     * Коллекция `Deque`, которую необходимо очистить.
     */
    private Deque deq;

    /**
     * Конструктор класса `Clear`.
     *
     * @param deque Коллекция `Deque`, которую необходимо очистить.
     */
    public Clear(Deque deque) {
        deq = deque;
    }

    /**
     * Выполняет команду очистки коллекции `Deque`.
     * Удаляет все элементы из коллекции `Deque`.
     */
    @Override
    public void execute() {
        deq.clear();
    }

}
