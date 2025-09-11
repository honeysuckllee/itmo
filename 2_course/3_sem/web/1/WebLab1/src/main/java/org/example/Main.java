package org.example;

import com.fastcgi.FCGIInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    private static final String HTTP_RESPONSE = """
            Status: 200 OK
            Content-Type: application/json

            %s
            """;
    private static final String HTTP_ERROR = """
            Status: 400 Bad Request
            Content-Type: application/json

            %s
            """;
    private static final String RESULT_JSON = """
            {
                "result": %b,
                "curr_time": "%s",
                "exec_time": "%s"
            }
            """;
    private static final String ERROR_JSON = """
            {
                "now": "%s",
                "reason": "%s"
            }
            """;

    public static void main(String[] args) {
        var fcgi = new FCGIInterface();
        var hitChecker = new Hit();
        var startTime = Instant.now();
        System.setProperty("FCGI_PORT", "9000");
        while (fcgi.FCGIaccept() >= 0) {
            try {
                var queryParams = System.getProperty("QUERY_STRING");
                if (queryParams == null || queryParams.trim().isEmpty()) {
                    errorResponse("Отсутствует QUERY_STRING");
                    continue; 
                }

                var parser = new Parser(queryParams);

                boolean result = hitChecker.hit(parser.getX(), parser.getY(), parser.getR());
                var endTime = Instant.now();

                long execNanos = ChronoUnit.NANOS.between(startTime, endTime);
                double execMillis = execNanos / 1_000_000.0;
                String execTimeStr = String.format("%.3fms", execMillis);

                String json = String.format(
                        RESULT_JSON,
                        result,
                        LocalDateTime.now(),
                        execTimeStr
                );

                String response = String.format(
                        HTTP_RESPONSE,
                        json
                );
                System.out.print(response);

            } catch (ExceptionMessage e) {
                errorResponse(e.getMessage());
            } catch (Exception e) {
                errorResponse("Внутренняя ошибка сервера: " + e.getMessage());
            }
        }
    }

    private static void errorResponse(String message) {
        String json = String.format(ERROR_JSON, LocalDateTime.now(), message);
        String response = String.format(
                HTTP_ERROR,
                json
        );
        System.out.print(response);
    }
}