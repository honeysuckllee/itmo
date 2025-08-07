package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static ru.lab6.Service.Utilites.getValidInt;
import static ru.lab6.Service.Utilites.integerConverter;

/**
 * Класс `RemoveById` реализует интерфейс `Command` и представляет команду удаления элемента из коллекции по его идентификатору.
 * Если идентификатор не был передан, он запрашивается у пользователя.
 */
public class RemoveById extends Command {
    /**
     * Коллекция `Deque`, из которой удаляется элемент.
     */
    private Deque deque;

    /**
     * Идентификатор элемента, который необходимо удалить.
     */
    private Integer id;

    /**
     * Конструктор класса `RemoveById`.
     *

     * @param deque Коллекция `Deque`, из которой удаляется элемент.

     */
    public RemoveById( Deque deque) {
        this.deque = deque;
    }

    /**
     * Метод `execute` удаляет элемент из коллекции по его идентификатору.
     * Если идентификатор не был передан, он запрашивается у пользователя.
     */

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        if (request.getArg().isEmpty()){
            id = getValidInt( request.isScript(),  inputStream, outputStream, "Введите id:");
        }
        else {
            id = integerConverter(request.getArg());
        }
        if (id != null)
        {
            boolean dell = deque.removeById(id);
            if (dell) {
                return new Response("Элемент удален\n", true);
            }
            return new Response("Элемент не может быть удален\n", true);
        }
        else {
            return new Response("Неверный формат команды \n", true);
        }
    }
}