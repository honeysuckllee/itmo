package org.example;

import org.example.Control.CommandManager;
import org.example.Model.Deque;

/**
 * Главный класс приложения, содержащий точку входа в программу.
 */
public class Main {
    /**
     * Основной метод, с которого начинается выполнение программы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {

        Deque deq = new Deque();
        if (deq.isError()){
            return;
        }

        if (!(deq.getCollectionFileName() == null)) {
            CommandManager commandManager = new CommandManager(deq);
            commandManager.run();}
        }
    }

