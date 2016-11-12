package com.leafgraph.tshimizu.sysdev.memcached;

import com.leafgraph.tshimizu.sysdev.memcached.presentation.MemcachedServer;

public class Main {


    public static void main(String[] args) {
	// write your code here
        MemcachedServer server=new MemcachedServer();
        server.start();
    }
}
