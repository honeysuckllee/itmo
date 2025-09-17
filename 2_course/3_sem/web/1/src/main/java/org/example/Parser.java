package org.example;
import lombok.*;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.example.Validator.validateParams;

@Data
public class Parser {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;

    public Parser(String info) throws ExceptionMessage {
        if (info == null || info.isEmpty()) {
            throw new ExceptionMessage("Запрос пуст");
        }
        Map<String, String> parameters = splitInfo(info);
        validateParams(parameters);
        this.x = new BigDecimal(parameters.get("x"));
        this.y = new BigDecimal(parameters.get("y"));
        this.r = new BigDecimal(parameters.get("r"));
    }

    private static Map<String, String> splitInfo(String info) {
        return Arrays.stream(info.split("&"))
                .map(pair -> pair.split("="))
                .collect(
                        Collectors.toMap(
                                pairParts -> URLDecoder.decode(pairParts[0], StandardCharsets.UTF_8),
                                pairParts -> URLDecoder.decode(pairParts[1], StandardCharsets.UTF_8),
                                (a, b) -> b,
                                HashMap::new
                        )
                );
    }
}