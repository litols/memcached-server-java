package com.leafgraph.tshimizu.sysdev.memstashd;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.IllegalFormatException;

/**
 * Created by takahiro on 2016/10/24.
 */
public class MemstashdServer {
    HashMap<String,String> dataStore;

    public MemstashdServer() {
        dataStore=new HashMap<>();
    }

    public void start(){
        try{
            ServerSocket server= new ServerSocket(Util.SERVER_PORT_NUMBER);
            while(true){
                this.serverProcess(server);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void serverProcess(ServerSocket server) throws IOException{
        Socket socket=server.accept();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            //parse command
            String commandString = reader.readLine();
            String[] commands = commandString.split(" ");
            if (commands[0] == null) {
                //no-op.
                //throw new IllegalFormatException("can't parse command.");
            } else switch (commands[0]) {
                case "get":
                    //TODO:存在チェック
                    String gdata = dataStore.get(commands[1]);
                    writer.write(gdata);
                    writer.newLine();
                    writer.write("END");
                    writer.flush();
                    break;
                case "set":
                    String sdata = reader.readLine();
                    dataStore.put(commands[1], sdata);
                    System.out.println(sdata+" STORED.");
                    writer.write("STORED");
                    writer.flush();
                    break;
                default:
                    System.out.println("COMMAND:"+commands[0]+" NOT IMPLEMENTED.");
            }
        }catch (IOException e){
            throw  new UncheckedIOException(e);
        }finally {
            try {
                socket.close();
            }catch (IOException e){

            }
        }


    }
}
