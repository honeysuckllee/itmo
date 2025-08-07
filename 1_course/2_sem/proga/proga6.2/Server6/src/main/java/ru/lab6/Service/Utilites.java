package ru.lab6.Service;

import ru.lab6.Model.Coordinates;
import ru.lab6.Model.Location;
import ru.lab6.Requests.Request;
import ru.lab6.Response;
import ru.lab6.exceptions.InvalidAmountOfArgumentsException;

import java.io.*;
import java.util.Scanner;

/**
 * Класс вспомогательных методов.
 */
public class Utilites {


    /**
     * Возвращает незультат проверки количества аргументов
     *
     * @return boolean.
     */
    public static boolean verify(String[] cmdSplit, int argsAmount) throws InvalidAmountOfArgumentsException {
        boolean ver = cmdSplit.length == argsAmount + 1;
        if (!ver) throw new InvalidAmountOfArgumentsException(argsAmount);
        return true;
    }

    /**
     * Конвертирует Integer в Long.
     *
     * @param number Integer.
     * @return Long.
     */
    public static Long longConverter(Integer number) {
        Long converted = null;
        try {
            converted = Long.valueOf(number);
        } catch (NumberFormatException e) {
            System.err.println("При загрузке данных произошла ошибка. Ошибка типов данных.");
        }
        return converted;
    }

    /**
     * Конвертирует String в Float.
     *
     * @param number String.
     * @return Float.
     */
    public static Float floatConverter(String number) {
        float converted = 0;
        try {
            converted = Float.valueOf(number);
        } catch (NumberFormatException e) {
            System.err.println("При загрузке данных произошла ошибка. Проверьте формат формат ввода, вы должы ввести число c плавающей точкой.");
        }
        return converted;
    }

    /**
     * Конвертирует String в Integer.
     *
     * @param number String.
     * @return Integer.
     */
    public static Integer integerConverter(String number) {
        Integer converted = null;
        try {
            converted = Integer.valueOf(number);
        } catch (NumberFormatException e) {
            System.err.println("При загрузке данных произошла ошибка. Проверьте формат формат ввода, вы должы ввести целое число.");
        }
        return converted;
    }

    /**
     * Конвертирует String в Double.
     *
     * @param number String.
     * @return Double.
     */
    public static Double doubleConverter(String number) {
        Double converted = null;
        try {
            converted = Double.valueOf(number);
        } catch (NumberFormatException e) {
            System.err.println("При загрузке данных произошла ошибка. Проверьте формат формат ввода, вы должы ввести число с плавающей точкой.");
        }
        return converted;
    }

    /**
     * Запрашивает у пользователя корректное имя, состоящее только из букв и пробелов.
     *
     * @return Введенное пользователем корректное имя.
     */
    public static String getValidName(boolean enable_out, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        String name = "";
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            if (!enable_out){
                objectOutputStream.writeObject(new Response("Введите name: "));
            }else{
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            name = request.getCommand().trim();

            if (name.isEmpty()) {
                if (!enable_out) {
                    objectOutputStream.writeObject(new Response("Ошибка: name не может быть пустым."));
                }
                else {
                    objectOutputStream.writeObject(new Response(""));
                }
            } else if (!name.matches("[a-zA-Zа-яА-Я\\s]+")) {
                if (!enable_out) {
                    objectOutputStream.writeObject(new Response("Ошибка: name должно содержать только буквы и пробелы."));
                }
                else {
                    objectOutputStream.writeObject(new Response(""));
                }
            } else {
                isValid = true;
            }
        }
        return name;
    }

    /**
     * Запрашивает у пользователя целое число.
     *
     * @param promt   Приглашение для ввода.
     * @return Введенное пользователем целое число.
     */
    public static int getValidInt(boolean enable_out, InputStream inputStream, OutputStream outputStream, String promt) throws IOException, ClassNotFoundException {
        int value = 0;
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            if (!enable_out){
                objectOutputStream.writeObject(new Response(promt));
            }
            else {
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();

            if (request.getCommand() != null ) {
                Integer tryValue = integerConverter(request.getCommand().trim());
                if (tryValue != null) {
                    value = tryValue;
                    isValid = true;
                }
            } else {
                objectOutputStream.writeObject(new Response("Ошибка: введено не число типа double. Пожалуйста, введите число."));
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя число типа double.
     *
     * @return Введенное пользователем число типа double.
     */
    public static double getValidDouble(boolean enable_out, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        double value = 0.0;
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (!enable_out){
                objectOutputStream.writeObject(new Response("Введите число типа double : "));
            }
            else {
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            if (request.getCommand() != null) {
                Double tryValue = doubleConverter(request.getCommand().trim());
                if (tryValue != null) {
                    value = tryValue;
                    isValid = true;
                }
            }
            //else {
            //    objectOutputStream.writeObject(new Response("Ошибка: введено не число типа double. Пожалуйста, введите число."));
            //}
        }
        return value;
    }

    /**
     * Запрашивает у пользователя число типа float.
     *
     * @param promt   Приглашение для ввода.
     * @return Введенное пользователем число типа float.
     */
    public static float getValidFloat(boolean enable_out, InputStream inputStream, OutputStream outputStream, String promt) throws IOException, ClassNotFoundException {
        float value = 0.0f;
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (!enable_out){
                objectOutputStream.writeObject(new Response(promt));
            }
            else {
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();


            if (request.getCommand() != null) {
                Float tryValue = floatConverter(request.getCommand().trim());
                if (tryValue != null) {
                    value = tryValue;
                    isValid = true;
                }
            } else {
                objectOutputStream.writeObject(new Response("Ошибка: введено не число типа float. Пожалуйста, введите число."));
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя число типа float, большее 1.
     *
     * @return Введенное пользователем число типа float, большее 1 или null если нажат Ввод.
     */
    public static Float getValidFloatDistance(boolean enable_out, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        float value = 0.0f;
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (!enable_out){
                objectOutputStream.writeObject(new Response("Введите distance типа float больше 1: "));
            }
            else {
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();

            if (request.getCommand() != null) {
                Float tryValue = floatConverter(request.getCommand().trim());
                if (tryValue != null) {
                    if (tryValue > 1) {
                        value = tryValue;
                        isValid = true;
                    } //else {
                        //objectOutputStream.writeObject(new Response("Ошибка: введено число меньше 1 \n"));
                    //}
                }
            } else {
                objectOutputStream.writeObject(new Response("Ошибка: введено не число типа float. Пожалуйста, введите число."));
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя число типа float, большее -334.
     *
     * @return Введенное пользователем число типа float, большее -334.
     */
    public static float getValidFloatCoordinates(boolean enable_out, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        float value = 0.0f;
        boolean isValid = false;

        while (!isValid) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (!enable_out) {
                objectOutputStream.writeObject(new Response("Введите число типа float больше -334: "));
            }
            else {
                objectOutputStream.writeObject(new Response(""));
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            if (request.getCommand() != null) {
                Float tryValue = floatConverter(request.getCommand().trim());
                if (tryValue != null) {
                    value = tryValue;

                    if (value > -334) {
                        isValid = true;
                    }
                }
            }
        }
        return value;
    }
    //
    /**
     * Запрашивает у пользователя координаты и создает объект Coordinates.
     *
     * @return Объект Coordinates с введенными пользователем координатами.
     */
    public static Coordinates getValidCoordinates(boolean enable_out, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        double x = getValidDouble(enable_out,inputStream, outputStream);
        float y = getValidFloatCoordinates(enable_out,inputStream, outputStream);
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает у пользователя данные о локации и создает объект Location.
     *
     * @return Объект Location с введенными пользователем данными.
     */
    public static Location getValidLocation(boolean enable_out, InputStream inputStream, OutputStream outputStream, String promt) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        if (!enable_out){

            objectOutputStream.writeObject(new Response(promt + "Введите ответ 'yes' если значение типа location не равно null: "));
            promt = "";
        }
        else
        {
            objectOutputStream.writeObject(new Response(""));
        }
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Request request = (Request) objectInputStream.readObject();

        // Если ввод пустой, возвращаем null
        if (request.getCommand() == null || !request.getCommand().equalsIgnoreCase("yes")) {
            return null;
        }

        int x = getValidInt(enable_out, inputStream, outputStream, "Введите Location x:");
        Float y = getValidFloat(enable_out, inputStream, outputStream,"Введите Location y:");
        int z = getValidInt(enable_out, inputStream, outputStream, "Введите Location z:");
        return new Location(x, y, z);
    }
}
