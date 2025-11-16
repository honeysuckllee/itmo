package org.example.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Named("timeBean")
@SessionScoped
public class TimeBean implements Serializable {

    private long serverTimeMillis;
    private ScheduledExecutorService updater;


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
        this.serverTimeMillis = System.currentTimeMillis();
    }

    public long getServerTimeMillis() {
        return serverTimeMillis;
    }
}