package org.example.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.example.dao.UserDao;
import org.example.entity.UserEntity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Stateless
public class UserServiceBean {
    @EJB
    private UserDao userDao;

    public boolean registerUser(String username, String password) {
        if (userDao.findUserByUsername(username) != null) {
            return false;
        }

        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);
        System.out.println("User '" + username + "' registered successfully.");

        return userDao.registerUser(username, passwordHash, salt);
    }

    public UserEntity authenticateUser(String username, String password) {
        UserEntity user = userDao.findUserByUsername(username);

        if (user == null) {
            return null;
        }

        // хэшируем введенный пароль с той же солью что хранится в базе
        String inputPasswordHash = hashPassword(password, user.getSalt());

        if (inputPasswordHash.equals(user.getPasswordHash())) {
            System.out.println("User '" + username + "' authenticated successfully.");
            return user;
        }
        return null;
    }


    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hashedBytes = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }
}