package ru.lab6.Commands;

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

/**
 *
 * Класс `Add` реализует команду добавления нового элемента `Route` в коллекцию `Deque`.
 */
public class Add extends Command {
    /**
     * Имя маршрута. Поле не может быть null, строка не может быть пустой.
     */
    private String name;
    /**
     * Координаты маршрута. Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Дата создания маршрута. Поле не может быть null, значение этого поля должно генерироваться автоматически.
     */
    private LocalDate creationDate;
    /**
     * Начальная локация маршрута. Поле может быть null.
     */
    private Location from;
    /**
     * Конечная локация маршрута. Поле может быть null.
     */
    private Location to;
    /**
     * Дистанция маршрута.
     */
    private Float distance;
    /**
     * Коллекция `Deque`, в которую добавляется маршрут.
     */
    private Deque deque;

    /**
     * Конструктор класса `Add`.
     *
     * @param deque   Коллекция `Deque`, в которую добавляется маршрут.
     */
    public Add(Deque deque){
        this.deque = deque;
    }

    /**
     * Выполняет команду добавления нового элемента `Route` в коллекцию.
     * Запрашивает у пользователя данные для создания объекта `Route` и добавляет его в коллекцию `Deque`.
     */

    @Override
    public Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        // id
        //int id = route.getId();

        //  name

        if (!request.getArg().isEmpty()){
            this.name = request.getArg();
        }else{
            this.name = getValidName(request.isScript(), inputStream, outputStream);
        }

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

        deque.addRoute(name, coordinates, creationDate, from, to, distance);

        return new Response("Маршрут добавлен" + "\n", true);

    }
}
