package com.leafgraph.tshimizu.sysdev.memstashd.infra;

/**
 * Created by takahiro on 2016/11/08.
 */
public class ProccessCounter {
    private long cmd_get=0;
    private long cmd_set=0;
    private long get_hits=0;
    private long get_misses=0;

    private static ProccessCounter proccessCounterInstance = new ProccessCounter();

    private ProccessCounter(){
    }

    public synchronized static ProccessCounter getSingleton(){
        return proccessCounterInstance;
    }

    public synchronized void increment_cmd_get(){
        synchronized (ProccessCounter.class){
            cmd_get++;
        }
    }

    public synchronized void increment_cmd_set(){
        synchronized (ProccessCounter.class){
            cmd_set++;
        }
    }
    public synchronized void increment_get_hits(){
        synchronized (ProccessCounter.class){
            get_hits++;
        }
    }
    public synchronized void increment_get_misses(){
        synchronized (ProccessCounter.class){
            get_misses++;
        }
    }

    public long getCmd_get() {
        return cmd_get;
    }

    public long getCmd_set() {
        return cmd_set;
    }

    public long getGet_hits() {
        return get_hits;
    }

    public long getGet_misses() {
        return get_misses;
    }
}
