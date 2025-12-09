package org.example.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Provider //регистрация как фильтра
@Priority(Priorities.AUTHENTICATION) // фильтр аутентификации выполняется раньше всех фильтров
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length()).trim();
            try {
                String secretKey = System.getProperty("jwt.secret");
                if (secretKey == null) {
                    return;
                }
                // расшифровка токена
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();

                if (username != null) {
                    final SecurityContext securityContext = requestContext.getSecurityContext();
                    requestContext.setSecurityContext(new SecurityContext() { //глобальный объект со всей инфой о текущем пользователе
                        @Override
                        public Principal getUserPrincipal() {
                            return () -> username;
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            return true;
                        }

                        @Override
                        public boolean isSecure() {
                            return false;
                        } //HTTP

                        @Override
                        public String getAuthenticationScheme() {
                            return "Bearer";
                        } // тип аутентификации
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}