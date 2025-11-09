package org.example.jsf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.example.Point;
import org.example.util.PointDAO;

import java.io.Serializable;
import java.util.List;
@Named("controllerBean")
@SessionScoped

public class ControllerBean implements Serializable {
    private final PointDAO pointDAO = new PointDAO();
    private List<Point> points = pointDAO.findAll();
    private String PointsAsJson = "[]";

    public ControllerBean() {
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    public List<Point> loadResult(){
        return points;
    }
    public void clear() {
        points.clear();
        pointDAO.deleteAll();
    }

    public void addPoint(Point point) {
        points.add(0, point);
        pointDAO.save(point);
    }

    public String getPointsAsJson() throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(points);
        } catch (Exception e) {
            return "[]";
        }
    }
    public void setPointsAsJson(String PointsAsJson){
        this.PointsAsJson = PointsAsJson;
    }
}
