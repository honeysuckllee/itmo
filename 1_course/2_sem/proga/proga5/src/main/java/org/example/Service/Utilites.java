package org.example.Service;

import org.example.Model.Coordinates;
import org.example.Model.Location;

import java.util.Scanner;

/**
 * Класс вспомогательных методов.
 */
public class Utilites {
    private static TransparentScannerWrapper scn = new TransparentScannerWrapper(new Scanner(System.in), true);

    /**
     * Возвращает объект класса Scanner.
     *
     * @return объект Scanner.
     */
    public static TransparentScannerWrapper scanner() {
        return scn;
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
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Введенное пользователем корректное имя.
     */
    public static String getValidName(TransparentScannerWrapper scanner) {
        String name = "";
        boolean isValid = false;

        while (!isValid) {
            if (scanner.enable_out){
            System.out.print("Введите name: ");}
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Ошибка: name не может быть пустым.");
            } else if (!name.matches("[a-zA-Zа-яА-Я\\s]+")) {
                System.out.println("Ошибка: name должно содержать только буквы и пробелы.");
            } else {
                isValid = true;
            }
        }
        return name;
    }

    /**
     * Запрашивает у пользователя целое число.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @param promt   Приглашение для ввода.
     * @return Введенное пользователем целое число.
     */
    public static int getValidInt(TransparentScannerWrapper scanner, String promt) {
        int value = 0;
        boolean isValid = false;
        if (scanner.enable_out){
        System.out.print(promt);}
        while (!isValid) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера после nextInt()
                isValid = true;
            } else {
                System.out.println("Ошибка: введено не число. Пожалуйста, введите целое число.");
                scanner.next(); // Очистка некорректного ввода
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя число типа double.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Введенное пользователем число типа double.
     */
    public static double getValidDouble(TransparentScannerWrapper scanner) {
        double value = 0.0;
        boolean isValid = false;

        while (!isValid) {
            if (scanner.enable_out){
            System.out.print("Введите число типа double : ");}

            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine(); // Очистка буфера после nextDouble()
                isValid = true;
            } else {
                System.out.println("Ошибка: введено не число типа double. Пожалуйста, введите число.");
                scanner.next(); // Очистка некорректного ввода
            }
        }

        return value;
    }

    /**
     * Запрашивает у пользователя число типа float.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @param promt   Приглашение для ввода.
     * @return Введенное пользователем число типа float.
     */
    public static float getValidFloat(TransparentScannerWrapper scanner, String promt) {
        float value = 0.0f;
        boolean isValid = false;
        if (scanner.enable_out){
        System.out.print(promt);}
        while (!isValid) {
            if (scanner.hasNextFloat()) {
                value = scanner.nextFloat();
                scanner.nextLine(); // Очистка буфера после nextFloat()
                isValid = true;
            } else {
                System.out.println("Ошибка: введено не число типа float. Пожалуйста, введите число.");
                scanner.next(); // Очистка некорректного ввода
            }
        }

        return value;
    }

    /**
     * Запрашивает у пользователя число типа float, большее 1.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Введенное пользователем число типа float, большее 1 или null если нажат Ввод.
     */
    public static Float getValidFloatDistance(TransparentScannerWrapper scanner) {
        if (scanner.enable_out){
        System.out.print("Введите ответ 'yes' если значение типа distance не равно null: ");}
        String inputY = scanner.nextLine().trim(); // Считываем всю строку и удаляем пробелы

        // Если ввод пустой, возвращаем null
        if (!inputY.equals("yes")) {
            return null;
        }

        while (true) {
            if (scanner.enable_out){
            System.out.print("Введите число типа float больше 1 (или нажмите Enter для пустого значения): ");}
            String input = scanner.nextLine().trim(); // Считываем всю строку и удаляем пробелы

            // Если ввод пустой, возвращаем null
            if (input.isEmpty()) {
                return null;
            }

            try {
                // Пытаемся преобразовать ввод в float
                float value = Float.parseFloat(input);

                // Проверяем, что число больше 1
                if (value > 1) {
                    return value;
                } else {
                    System.out.println("Ошибка: введено число меньше или равное 1. Пожалуйста, введите число больше 1.");
                }
            } catch (NumberFormatException e) {
                // Если ввод не является числом, выводим сообщение об ошибке
                System.out.println("Ошибка: введено не число типа float. Пожалуйста, введите число.");
            }
        }
    }

    /**
     * Запрашивает у пользователя число типа float, большее -334.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Введенное пользователем число типа float, большее -334.
     */
    public static float getValidFloatCoordinates(TransparentScannerWrapper scanner) {
        float value = 0.0f;
        boolean isValid = false;

        while (!isValid) {
            if (scanner.enable_out){
            System.out.print("Введите число типа float больше -334: ");}

            if (scanner.hasNextFloat()) {
                value = scanner.nextFloat();

                scanner.nextLine(); // Очистка буфера после nextFloat()
                if (value > -334) {
                    isValid = true;
                } else {
                    System.out.println("Ошибка: введено число меньше чем -334");
                }
            } else {
                System.out.println("Ошибка: введено не число типа float. Пожалуйста, введите число.");
                scanner.next(); // Очистка некорректного ввода
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя координаты и создает объект Coordinates.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Объект Coordinates с введенными пользователем координатами.
     */
    public static Coordinates getValidCoordinates(TransparentScannerWrapper scanner) {
        double x = getValidDouble(scanner);
        float y = getValidFloatCoordinates(scanner);
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает у пользователя данные о локации и создает объект Location.
     *
     * @param scanner Объект Scanner для чтения ввода пользователя.
     * @return Объект Location с введенными пользователем данными.
     */
    public static Location getValidLocation(TransparentScannerWrapper scanner) {
        if (scanner.enable_out){
        System.out.print("Введите ответ 'yes' если значение типа location не равно null: ");}
        String input = scanner.nextLine().trim(); // Считываем всю строку и удаляем пробелы

        // Если ввод пустой, возвращаем null
        if (!input.equals("yes")) {
            return null;
        }

        int x = getValidInt(scanner, "Введите Location x:");
        Float y = getValidFloat(scanner, "Введите Location y:");
        int z = getValidInt(scanner, "Введите Location z:");
        return new Location(x, y, z);
    }
}
