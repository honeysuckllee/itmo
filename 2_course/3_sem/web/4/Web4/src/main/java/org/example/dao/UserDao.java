package org.example.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.entity.UserEntity;

@Stateless
public class UserDao {
    @PersistenceContext(unitName = "my-pu")
    private EntityManager em;

    public boolean registerUser(String username, String passwordHash, String salt) {
        if (findUserByUsername(username) != null) {
            return false;
        }
        UserEntity user = new UserEntity(username, passwordHash, salt);
        em.persist(user);
        return true;
    }

    public UserEntity findUserByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}