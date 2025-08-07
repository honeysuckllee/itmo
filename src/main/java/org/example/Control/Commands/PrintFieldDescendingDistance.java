package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;

/**
 * Команда для печати элементов коллекции в порядке убывания расстояния до указанного поля.
 */
public class PrintFieldDescendingDistance implements Command {

    /**
     * Коллекция, с которой будет выполняться операция сортировки и вывода.
     */
    private final Deque deque;

    /**
     * Конструктор команды.
     *
     * @param deque коллекция
     */
    public PrintFieldDescendingDistance(Deque deque) {
        this.deque = deque;
    }

    /**
     * Выполняет команду печати элементов коллекции  в порядке убывания.
     */
    @Override
    public void execute() {
        deque.printFieldDescendingDistance();
    }
}