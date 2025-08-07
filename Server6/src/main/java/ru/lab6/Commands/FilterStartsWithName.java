package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static ru.lab6.Service.Utilites.*;

/**
 * Класс `FilterStartsWithName` реализует интерфейс `Command` и представляет команду фильтрации элементов коллекции,
 * которые начинаются с указанного имени.
 */
public class FilterStartsWithName extends Command {
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
     * @param deque Коллекция `Deque`, с которой работает команда.
     */
    public FilterStartsWithName(Deque deque) {
        this.deque = deque;
    }

    /**
     * Метод `execute` выполняет команду фильтрации элементов коллекции, которые начинаются с указанного имени.
     * Если имя не было передано, запрашивает его у пользователя.
     */

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        if (request.getArg().isEmpty()){
            name = getValidName( request.isScript(),  inputStream, outputStream);
        }
        else {
            name = request.getArg();
        }
        if (!name.isEmpty())
        {
            return new Response(deque.filterStartsWithName(name), true);
        }
        return new Response("Неверный формат команды \n", true);
    }
}