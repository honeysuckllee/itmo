package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Команда для печати элементов коллекции в порядке убывания расстояния до указанного поля.
 */
public class PrintFieldDescendingDistance extends Command {

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
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        return new Response(deque.printFieldDescendingDistance() + "\n", true);
    }
}