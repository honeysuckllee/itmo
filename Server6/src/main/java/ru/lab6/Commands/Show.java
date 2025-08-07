package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.Model.Route;
import ru.lab6.Requests.Request;
import ru.lab6.Response;
import ru.lab6.exceptions.CommandException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Map;

public class Show extends Command {
    private Deque deque;

    public Show(Deque deque) {
        this.deque = deque;
    }

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) {
        StringBuilder rez = new StringBuilder("Коллекция:\n");
        if (!deque.isEmpty()) {
            for (Route route : deque.getDeque()) {
                rez.append(route.toString()).append("\n");
            }
        }
        else {
            rez.append("пуста").append("\n");
        }
        return new Response(rez.toString(), true);
    }
}
