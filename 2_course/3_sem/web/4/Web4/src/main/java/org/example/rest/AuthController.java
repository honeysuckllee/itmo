package org.example.rest;

import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.ejb.UserServiceBean;
import org.example.entity.UserEntity;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.core.Cookie;
import java.util.Date;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @EJB
    private UserServiceBean userService;

    @POST
    @Path("/register")
    public Response registerUser(@Valid UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().trim().isEmpty() ||
                userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap("error", "Username and password cannot be empty"))
                    .build();
        }

        boolean isRegistered = userService.registerUser(userDto.getUsername(), userDto.getPassword());

        if (isRegistered) {
            return Response.status(Response.Status.CREATED)
                    .entity(Collections.singletonMap("message", "User registered successfully"))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap("error", "User with this username already exists"))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response loginUser(@Valid UserDto userDto) {
        UserEntity authenticatedUser = userService.authenticateUser(userDto.getUsername(), userDto.getPassword());

        if (authenticatedUser != null) {
            String token = Jwts.builder()
                    .setSubject(authenticatedUser.getUsername())
                    .setIssuedAt(new Date()) // когда выдан
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // срок действия сутки
                    .signWith(SignatureAlgorithm.HS256, System.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8)) // подпись секретным ключом
                    .compact();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Collections.singletonMap("error", "Invalid username or password"))
                    .build();
        }
    }
}