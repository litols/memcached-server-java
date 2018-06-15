package com.leafgraph.memcachedserv;

import com.leafgraph.memcachedserv.presentation.MemcachedServer;

public class Main {


    public static void main(String[] args) {
	// write your code here
        MemcachedServer server=new MemcachedServer();
        server.start();
    }
}
