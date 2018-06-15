package com.leafgraph.memcachedserv.infra;

import com.leafgraph.memcachedserv.infra.dao.Dao;
import com.leafgraph.memcachedserv.infra.dao.HashMapDao;
import com.leafgraph.memcachedserv.util.CRLFBufferedReader;

import java.io.*;
import java.util.Map;

/**
 * Created by takahiro on 2016/11/02.
 */


public class DataRepository {
    private static DataRepository dataRepositoryInstance = new DataRepository();

    private Dao dao;

    private DataRepository(){
        dao= HashMapDao.getSingleton();
        restoreDataRepository();
    }

    public synchronized static DataRepository getSingleton(){
        return dataRepositoryInstance;
    }

    public boolean write(String key, String value) {
        return dao.write(key, value);
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


    public int countItems(){
        return dao.countItems();
    }

    public boolean restoreDataRepository(){
        System.out.println("Restoreing KVS deta...");
        synchronized (DataRepository.class) {
            try {
                File[] files=new File("./data/").listFiles((dir, name) -> name.endsWith(".data.txt"));
                for(File file:files){
                    CRLFBufferedReader reader = new CRLFBufferedReader(new FileReader(file));
                    String key=reader.readLine();
                    String value=reader.readLine();
                    write(key,value);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return false;
            }
            System.out.println("All data restored.\n");
            return true;
        }
    }

    /**
     * 全dataをディスクに書き出す
     * @return
     */
    public boolean flushData(){
        for(Map.Entry<String,String> entry:dao.getDurtySet()){
            boolean ret=flushData(entry.getKey(), entry.getValue());
            if(!ret) {
                return false;
            } else {
                dao.deleteDurtySet(entry.getKey(), entry.getValue());
            }
        }

        return true;
    }

    /**
     * ディスクへのkeyに対応するNodeデータ書き込み
     * @param key
     * @param value
     * @return
     */
    public boolean flushData(String key, String value){
        synchronized (DataRepository.class) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./data/" + key + ".data.txt")));
                bw.write(key);
                bw.write("\r\n");
                bw.write(value);
                bw.flush();
                bw.close();
            } catch (Throwable e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
