package org.example;

import java.util.Map;

public class Validator {

    public static void validateParams(Map<String, String> parameters) throws ExceptionMessage {
        validateX(parameters.get("x"));
        validateY(parameters.get("y"));
        validateR(parameters.get("r"));
    }

    public static void validateX(String xValue) throws ExceptionMessage {
        if (xValue == null || xValue.isEmpty()) {
            throw new ExceptionMessage("недопустимый х");
        }

        try {
            int x = Integer.parseInt(xValue);
            if (x < -3 || x > 5) {
                throw new ExceptionMessage("недопустимый х");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionMessage("x не число");
        }
    }

    public static void validateY(String yValue) throws ExceptionMessage {
        if (yValue == null || yValue.isEmpty()) {
            throw new ExceptionMessage("недопустимый y");
        }

        try {
            float y = Float.parseFloat(yValue);
            if (y < -3 || y > 3) {
                throw new ExceptionMessage("недопустимый y");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionMessage("y не число");
        }
    }

    public static void validateR(String rValue) throws ExceptionMessage {
        if (rValue == null || rValue.isEmpty()) {
            throw new ExceptionMessage("недопустимый r");
        }

        try {
            int r = Integer.parseInt(rValue);
            if (r < 1 || r > 5) {
                throw new ExceptionMessage("недопустимый r");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionMessage("r не число");
        }
    }
}