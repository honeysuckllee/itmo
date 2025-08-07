package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Класс `Clear` реализует команду очистки коллекции `Deque`.
 */
public class Clear extends Command {
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
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        deq.clear();
        return new Response("Коллекция очищена\n", true);
    }
}
