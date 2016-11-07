package com.leafgraph.tshimizu.sysdev.memstashd.infra.dao;

import java.util.HashMap;

/**
 * Created by takahiro on 2016/11/02.
 */
public class HashMapDao extends Dao{
    private static HashMapDao hashMapDaoInstance =new HashMapDao();
    private static HashMap<String,String>hashMap = new HashMap<>();

    private HashMapDao() {

    }

    public synchronized static HashMapDao getSingleton(){
        return hashMapDaoInstance;
    }


    @Override
    public boolean create(String key,String value) {
        synchronized (HashMapDao.class){
            hashMap.put(key,value);
            return true;
        }
    }

    @Override
    public String read(String key) {
        synchronized (HashMapDao.class){
            if(!hashMap.containsKey(key))
                return null;
            return hashMap.get(key);
        }
    }

    @Override
    public boolean update(String key,String value) {
        synchronized (HashMapDao.class){
            hashMap.put(key,value);
            return true;
        }
    }

    @Override
    public boolean delete(String key) {
        synchronized (HashMapDao.class){
            hashMap.remove(key);
            return true;
        }
    }

    @Override
    public boolean containsKey(String key) {
        synchronized (HashMapDao.class) {
            return  hashMap.containsKey(key);
        }
    }

    @Override
    public int countItems() {
        synchronized (HashMapDao.class) {
            return hashMap.size();
        }
    }
}
