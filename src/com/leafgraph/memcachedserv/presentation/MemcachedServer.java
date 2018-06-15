package com.leafgraph.memcachedserv.presentation;

import com.leafgraph.memcachedserv.util.CRLFBufferedReader;
import com.leafgraph.memcachedserv.util.Settings;
import com.leafgraph.memcachedserv.domain.ServerUseCase;
import com.leafgraph.memcachedserv.util.ConnectionCloseByUserException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by takahiro on 2016/10/24.
 */
public class MemcachedServer {
    HashMap<String,String> dataStore;

    private ExecutorService service = Executors.newCachedThreadPool();
    public MemcachedServer() {
        dataStore=new HashMap<>();
    }

    public void start(){
        try{
            ServerSocket server= new ServerSocket(Settings.SERVER_PORT_NUMBER);
            while(true){
                this.serverProcess(server);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void serverProcess(ServerSocket server) throws IOException{
        Socket socket=server.accept();

        this.service.execute(() -> {
            try {
                CRLFBufferedReader reader = new CRLFBufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                while (!socket.isClosed()) {
                    ServerUseCase useCase = new ServerUseCase();
                    useCase.initProtocolIO(reader, writer);
                    useCase.parseCommand();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (ConnectionCloseByUserException e){
                // no-op.
            }finally {
                try {
                    if (socket != null & !socket.isClosed())
                        socket.close();
                } catch (IOException e) {
                    //no-op.
                }
            }
        });


    }
}
