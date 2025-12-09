package org.example.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.filter.JwtAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() { //регистрация rest компонентов
        Set<Class<?>> resources = new HashSet<>();
        resources.add(PointController.class);
        resources.add(AuthController.class);
        resources.add(JwtAuthenticationFilter.class);
        return resources;
    }
}