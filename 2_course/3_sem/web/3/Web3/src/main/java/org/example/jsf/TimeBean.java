package org.example.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Named("timeBean")
@SessionScoped
public class TimeBean implements Serializable {

    private String formattedDateTime;
    private long serverTimeMillis;
    private ScheduledExecutorService updater;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @PostConstruct
    public void init() {
        updater = Executors.newSingleThreadScheduledExecutor();
        updater.scheduleAtFixedRate(this::update, 0, 1, TimeUnit.SECONDS);
    }

    public void destroy() {
        if (updater != null) {
            updater.shutdownNow();
        }
    }
    public void update() {
        ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        this.formattedDateTime = moscowTime.format(FORMATTER);
        this.serverTimeMillis = moscowTime.toInstant().toEpochMilli();
        //this.serverTimeMillis = System.currentTimeMillis();
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public long getServerTimeMillis() {
        return serverTimeMillis;
    }
}