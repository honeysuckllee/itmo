package org.example;

import java.math.BigDecimal;
import java.util.Map;

public class Validator {

    public static void validateParams(Map<String, String> parameters) throws ExceptionMessage {
        validateX(parameters.get("x"));
        validateY(parameters.get("y"));
        validateR(parameters.get("r"));
    }
    public static boolean validSymvols(String value){
        return value.matches("[+-.,\\d]+");
    }

    public static void validateX(String xValue) throws ExceptionMessage {
        if (xValue == null || xValue.isEmpty()) {
            throw new ExceptionMessage("недопустимый х");
        }

        try {
            if (!validSymvols(xValue)){
                throw new ExceptionMessage("недопустимый x");
            }
            xValue = xValue.replace(",", ".");
            /*int x = Integer.parseInt(xValue);
            if (x < -3 || x > 5) {
                throw new ExceptionMessage("недопустимый х");
            }*/
            BigDecimal bd = new BigDecimal(xValue);

            if (bd.scale() > 0) {
                throw new ExceptionMessage("x должен быть целым числом");
            }

            int x = bd.intValueExact();
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
            if (!validSymvols(yValue)){
                throw new ExceptionMessage("недопустимый y");
            }
            yValue = yValue.replace(",", ".");
            BigDecimal y = new BigDecimal(yValue);

            BigDecimal min = new BigDecimal("-3");
            BigDecimal max = new BigDecimal("3");
            if (y.compareTo(min) < 0 || y.compareTo(max) > 0) {
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
            if (!validSymvols(rValue)){
                throw new ExceptionMessage("недопустимый r");
            }
            rValue = rValue.replace(",", ".");
            BigDecimal bd = new BigDecimal(rValue);

            if (bd.scale() > 0) {
                throw new ExceptionMessage("r должен быть целым числом");
            }

            int r = bd.intValueExact();
            if (r < 1 || r > 5) {
                throw new ExceptionMessage("недопустимый r");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionMessage("r не число");
        }
    }
}