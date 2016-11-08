package com.leafgraph.tshimizu.sysdev.memcached;

import com.leafgraph.tshimizu.sysdev.memcached.presentation.MemstashdServer;

public class Main {


    public static void main(String[] args) {
	// write your code here
        MemstashdServer server=new MemstashdServer();
        server.start();
    }
}
