package org.example.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.example.dao.ResultDao;
import org.example.entity.ResultEntity;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateless
public class HistoryServiceBean {
    @EJB
    private ResultDao resultDao;

    public void addResult(String username, ResultEntity result) {
        resultDao.saveResult(result);
    }

    public List<ResultEntity> getHistory(String username) {
        return resultDao.findResultsByUsername(username);
    }

    public void clearHistory(String username) {
        resultDao.clearHistory(username);
    }
}