package org.example.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.Point;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class PointDAO implements Serializable {
    @PersistenceContext(unitName = "myPU")
    private EntityManager em;
    public void save(Point point) {
        em.persist(point);
    }

    public List<Point> findAll() {
        List<Point> points = em.createQuery("SELECT u FROM Point u ORDER BY u.date DESC", Point.class).getResultList();
        return points;
    }

    public Point findById(Long id) {
        Point user = em.find(Point.class, id);
        return user;
    }
    @Transactional
    public void delete(Point user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Point").executeUpdate();
    }
}