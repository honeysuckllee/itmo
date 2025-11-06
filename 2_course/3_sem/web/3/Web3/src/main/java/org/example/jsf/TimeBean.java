package org.example.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Named("timeBean")
@SessionScoped
public class TimeBean implements Serializable {

    private String formattedDateTime;
    private long serverTimeMillis;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @PostConstruct
    public void init() {
        update();
    }

    public void update() {
        ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        this.formattedDateTime = moscowTime.format(FORMATTER);
        this.serverTimeMillis = moscowTime.toInstant().toEpochMilli();
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public long getServerTimeMillis() {
        return serverTimeMillis;
    }
}