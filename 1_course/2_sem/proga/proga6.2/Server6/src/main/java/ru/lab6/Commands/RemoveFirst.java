package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Класс `RemoveFirst` реализует интерфейс `Command` и представляет команду удаления первого элемента из коллекции.
 * При выполнении команды удаляется первый элемент коллекции.
 */
public class RemoveFirst extends Command {
    /**
     * Коллекция `Deque`, из которой удаляется первый элемент.
     */
    private Deque deque;

    /**
     * Конструктор класса `RemoveFirst`.
     *
     * @param deque Коллекция `Deque`, из которой удаляется первый элемент.
     */
    public RemoveFirst(Deque deque) {
        this.deque = deque;
    }

    /**
     * Метод `execute` удаляет первый элемент из коллекции.
     */

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        StringBuilder rez = new StringBuilder("\n");

        if (deque.removeFirst()){
            rez.append("Успешно удален первый элемент").append("\n");
        }
        else{
            rez.append("Коллекция пуста").append("\n");
        }
        return new Response(rez.toString(), true);
    }
}