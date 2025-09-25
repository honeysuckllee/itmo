package org.example;

import com.fastcgi.FCGIInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private static SQLManager sqlManager;

    public static void main(String[] args) {
        var fcgi = new FCGIInterface();
        var hitChecker = new Hit();
        sqlManager = new SQLManager();

        System.setProperty("FCGI_PORT", "26001");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        while (fcgi.FCGIaccept() >= 0) {
            try {
                var startTime = System.nanoTime();
                String requestMethod = System.getProperty("REQUEST_METHOD");
                if (requestMethod == null || !"GET".equalsIgnoreCase(requestMethod.trim())) {
                    errorResponseMethodNotAllowed("используйте метод GET.");
                    continue;
                }
                var queryParams = System.getProperty("QUERY_STRING");

                if (queryParams == null || queryParams.trim().isEmpty()) {
                    errorResponse("Отсутствует QUERY_STRING");
                    continue; 
                }

                if (queryParams.contains("clear=1")) {
                    sqlManager.clearHitResult();

                    String json = """
                    {
                        "status": "cleared",
                        "curr_time": "%s"
                    }
                    """.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));

                    String response = String.format(HTTP_RESPONSE, json);
                    System.out.print(response);

                    continue;
                }

                var parser = new Parser(queryParams);
                boolean result = hitChecker.hit(parser.getX(), parser.getY(), parser.getR());
                var endTime = System.nanoTime();
                double execMillis = (endTime - startTime) / 1_000_000.0;
                String execTimeStr = String.format("%.3fms", execMillis);
                String currTimeStr = outputFormatter.format(LocalDateTime.now());

                sqlManager.insertHitResult(
                        parser.getX(),
                        parser.getY(),
                        parser.getR(),
                        result,
                        execMillis,
                        currTimeStr
                );

                String json = String.format(
                        RESULT_JSON,
                        result,
                        currTimeStr,
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
    private static void errorResponseMethodNotAllowed(String message) {
        String json = String.format(ERROR_JSON, LocalDateTime.now(), message);
        String response = String.format(
                HTTP_ERROR,
                json
        );
        System.out.print(response);
    }
}