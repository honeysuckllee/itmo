package ru.lab6.Commands;

import ru.lab6.Commands.Command;
import ru.lab6.Model.Coordinates;
import ru.lab6.Model.Deque;
import ru.lab6.Model.Location;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

import static ru.lab6.Service.Utilites.*;

public class Update extends Command {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private Float distance;
    private Deque deque;
    private Integer id;

    public Update(Deque deque) {
        this.deque = deque;
    }

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        // id
        id = null;
        if (!request.getArg().isEmpty()){
            id = integerConverter(request.getArg());
        }
        if (id == null)
        {
            id = getValidInt(request.isScript(), inputStream, outputStream, "Введите id : ");
        }

        //  name
        this.name = getValidName(request.isScript(), inputStream, outputStream);

        //  coordinates
        this.coordinates = getValidCoordinates(request.isScript(), inputStream, outputStream);

        //  creationDate
        this.creationDate = LocalDate.now();

        //  from
        this.from = getValidLocation(request.isScript(), inputStream, outputStream, "Введите значение to: \n");

        //  to
        this.to = getValidLocation(request.isScript(), inputStream, outputStream, "Введите значение from: \n");

        //  distance
        this.distance = getValidFloatDistance(request.isScript(), inputStream, outputStream);

        deque.updateRoute(id, name, coordinates, creationDate, from, to, distance);

        return new Response("Маршрут обновлен" + "\n", true);

    }
}