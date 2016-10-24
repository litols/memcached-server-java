package com.leafgraph.tshimizu.sysdev.memstashd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.IllegalFormatException;

public class Main {


    public static void main(String[] args) {
	// write your code here
        MemstashdServer server=new MemstashdServer();
        server.start();
    }
}
