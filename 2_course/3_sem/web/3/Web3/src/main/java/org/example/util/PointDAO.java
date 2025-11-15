package org.example.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.Point;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class PointDAO implements Serializable {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");

    public void save(Point point) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(point);
        em.getTransaction().commit();
        em.close();
    }

    public List<Point> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Point> points = em.createQuery("SELECT u FROM Point u ORDER BY u.date DESC", Point.class).getResultList();
        em.close();
        return points;
    }

    public Point findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Point user = em.find(Point.class, id);
        em.close();
        return user;
    }

    public void delete(Point user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(user) ? user : em.merge(user));
        em.getTransaction().commit();
        em.close();
    }

    public void deleteAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Point").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
