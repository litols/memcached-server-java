package com.leafgraph.tshimizu.sysdev.memstashd.infra.dao;

/**
 * Created by takahiro on 2016/11/07.
 */
public abstract class Dao {
    public abstract boolean create(String key,String value);
    public abstract String read(String key);
    public abstract boolean update(String key,String value);
    public abstract boolean delete(String key);
    public abstract boolean containsKey(String key);

    public abstract int countItems();

}
