package com.leafgraph.tshimizu.sysdev.memstashd.infra;

import com.leafgraph.tshimizu.sysdev.memstashd.infra.dao.Dao;
import com.leafgraph.tshimizu.sysdev.memstashd.infra.dao.HashMapDao;

/**
 * Created by  on 2016/11/02.
 */


public class DataRepository {
    private static DataRepository dataRepositoryInstance = new DataRepository();

    private Dao dao;

    private DataRepository(){
        dao=HashMapDao.getSingleton();
    }

    public synchronized static DataRepository getSingleton(){
        return dataRepositoryInstance;
    }

    public boolean create(String key, String value) {
        return dao.create(key, value);
    }

    public String read(String key) {
        return dao.read(key);
    }

    public boolean delete(String key) {
        return dao.delete(key);
    }

    public boolean containsKey(String key) {
        return dao.containsKey(key);
    }

    public boolean update(String key, String value) {
        return dao.update(key, value);
    }
}
