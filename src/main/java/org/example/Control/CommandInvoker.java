package org.example.Control;

import org.example.Control.Command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Класс, отвечающий за выполнение команд и управление их историей.
 * Поддерживает возможность выполнения команд и сохранения их в истории.
 */
public class CommandInvoker {
    /**
     * История выполненных команд.
     * Используется Deque для хранения команд в порядке их выполнения.
     */
    private Deque<Command> commandHistory = new ArrayDeque<>();

    /**
     * Выполняет команду и сохраняет её в истории.
     *
     * @param command Команда для выполнения.
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command); // Сохраняем команду в истории
    }
}