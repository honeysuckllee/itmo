package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Coordinates;
import org.example.Model.Deque;
import org.example.Model.Location;
import org.example.Service.TransparentScannerWrapper;

import java.time.LocalDate;
import java.util.Scanner;

import static org.example.Service.Utilites.*;

public class Update implements Command {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private Float distance;
    private Deque deque;
    private int id;
    private TransparentScannerWrapper scanner;

    public Update( TransparentScannerWrapper scanner,Deque deque, int id) {
        this.scanner = scanner;
        this.deque = deque;
        this.id = id;
    }

    @Override
    public void execute() {
        if (id == 0){
            id = getValidInt(scanner, "Введите id:");
        }
        System.out.println("Ввод name");
        name = getValidName(scanner);
        System.out.println("Ввод Coordinates");
        coordinates = getValidCoordinates(scanner);
        creationDate = LocalDate.now();
        System.out.println("Ввод from:");
        from = getValidLocation(scanner);
        System.out.println("Ввод to:");
        to = getValidLocation(scanner);
        System.out.println("Ввод Distance");
        distance = getValidFloatDistance(scanner);
        deque.updateRoute(id, name, coordinates, creationDate, from, to, distance);

    }
}