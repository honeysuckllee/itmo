package org.example.rest;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.Context;
import org.example.dto.PointDto;
import org.example.ejb.HistoryServiceBean;
import org.example.ejb.PointServiceBean;
import org.example.entity.ResultEntity;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import jakarta.ws.rs.core.SecurityContext;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointController {

    @EJB
    private PointServiceBean pointService;

    @EJB
    private HistoryServiceBean historyService;

    @Context
    private SecurityContext securityContext;


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "OK";
    }


    @POST
    @Path("/check")
    public Response checkPoint(@Valid PointDto dto) {
        long startTime = System.currentTimeMillis();

        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Collections.singletonMap("error", "Authentication required"))
                    .build();
        }
        String username = userPrincipal.getName();

        try {
            BigDecimal x = new BigDecimal(dto.getX());
            BigDecimal y = new BigDecimal(dto.getY());
            BigDecimal r = new BigDecimal(dto.getR());

            boolean isHit = pointService.checkPoint(x, y, r);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            ResultEntity result = new ResultEntity(username, x, y, r, isHit, executionTime);

            historyService.addResult(username, result);

            return Response.ok(result).build();

        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap("error", "Invalid number format for x, y, or r"))
                    .build();
        }
    }

    @GET
    @Path("/history")
    public Response getHistory() {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String username = userPrincipal.getName();

        List<ResultEntity> userHistory = historyService.getHistory(username);
        return Response.ok(userHistory).build();
    }

    @DELETE
    @Path("/clear")
    public Response clearHistory() {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String username = userPrincipal.getName();
        historyService.clearHistory(username);
        return Response.noContent().build();
    }
}