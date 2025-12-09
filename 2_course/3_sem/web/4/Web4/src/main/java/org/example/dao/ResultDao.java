package org.example.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.ResultEntity;

import java.util.List;

@Stateless
public class ResultDao {
    @PersistenceContext(unitName = "my-pu")
    private EntityManager em;

    public void saveResult(ResultEntity result) {
        em.persist(result);
    }

    public List<ResultEntity> findResultsByUsername(String username) {
        return em.createQuery("SELECT r FROM ResultEntity r WHERE r.username = :username ORDER BY r.checkTime DESC", ResultEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

    public void clearHistory(String username) {
        em.createQuery("DELETE FROM ResultEntity r WHERE r.username = :username")
                .setParameter("username", username)
                .executeUpdate();
    }
}