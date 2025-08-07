package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Класс `Info` реализует интерфейс `Command` и представляет команду вывода информации о коллекции.
 * При выполнении команды выводятся тип коллекции, время создания и количество элементов.
 */
public class Info extends Command {
    /**
     * Коллекция `Deque`, для которой выводится информация.
     */
    private Deque deq;

    /**
     * Конструктор класса `Info`.
     *
     * @param deque Коллекция `Deque`, для которой выводится информация.
     */
    public Info(Deque deque) {
        deq = deque;
    }

    /**
     * Метод `execute` выводит информацию о коллекции, включая тип коллекции,
     * время создания и количество элементов.
     * Если возникает ошибка при получении времени создания, выбрасывается исключение `RuntimeException`.
     */
    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) {
        String rez = "Тип коллекции:" + deq.getCollectionType() + "\n";
        try {
            rez += "Время создания коллекции:" + deq.getCollectionFileCreationDate() + "\n";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rez += "Количество элементов в коллекции:" + deq.getDeque().size() + "\n";
        return new Response(rez, true);
    }
}