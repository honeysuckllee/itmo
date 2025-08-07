package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static ru.lab6.Service.Utilites.getValidInt;
import static ru.lab6.Service.Utilites.integerConverter;

public class RemoveLower extends Command {
    private Deque deque;
    private Integer id;

    public RemoveLower(Deque deque) {
        this.deque = deque;
    }

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
            if (!deque.getDeque().isEmpty()) {
                int counterDell = deque.removeLower(id);
                return new Response("Удалено " + counterDell + " элементов" + "\n", true);
            }
            return new Response("Коллекция пуста" + "\n", true);
        }
        else
        {
            return new Response("Неверный формат команды \n", true);
        }
    }
}
