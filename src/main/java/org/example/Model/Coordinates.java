package org.example.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий координаты.
 * Используется для хранения географических координат (широта и долгота).
 */
@Getter
@Setter
public class Coordinates {
    /**
     * Широта (latitude).
     */
    private double x;

    /**
     * Долгота (longitude).
     * Значение поля должно быть больше -334.
     */
    private float y; //Значение поля должно быть больше -334

    /**
     * Возвращает значение широты.
     *
     * @return Широта (latitude).
     */
    public double getLatitude() {
        return x;
    }

    /**
     * Возвращает значение долготы.
     *
     * @return Долгота (longitude).
     */
    public float getLongitude() {
        return y;
    }

    /**
     * Конструктор для создания объекта координат.
     *
     * @param x Широта (latitude).
     * @param y Долгота (longitude). Значение должно быть больше -334.
     */
    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }
}