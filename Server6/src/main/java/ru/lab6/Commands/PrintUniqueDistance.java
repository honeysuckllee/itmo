package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Класс `PrintUniqueDistance` реализует интерфейс `Command` и представляет команду вывода уникальных значений поля `distance` из коллекции.
 * При выполнении команды выводятся все уникальные значения поля `distance` элементов коллекции.
 */
public class PrintUniqueDistance extends Command {
    /**
     * Коллекция `Deque`, из которой извлекаются уникальные значения поля `distance`.
     */
    private Deque deque;

    /**
     * Конструктор класса `PrintUniqueDistance`.
     *
     * @param deque Коллекция `Deque`, из которой извлекаются уникальные значения поля `distance`.
     */
    public PrintUniqueDistance(Deque deque) {
        this.deque = deque;
    }

    /**
     * Метод `execute` выводит уникальные значения поля `distance` из коллекции.
     */

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        return new Response(deque.printUniqueDistance(), true);
    }
}