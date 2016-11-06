package com.leafgraph.tshimizu.sysdev.memstashd.domain;

import com.leafgraph.tshimizu.sysdev.memstashd.infra.DataRepository;
import com.leafgraph.tshimizu.sysdev.memstashd.util.ConnectionCloseByUserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by takahiro on 2016/11/02.
 */
public class ServerUseCase{
    private BufferedReader reader;
    private BufferedWriter writer;
    private DataRepository repository = DataRepository.getSingleton();

    public void initProtocolIO(BufferedReader reader, BufferedWriter writer) {
        this.reader=reader;
        this.writer=writer;
    }

    public void parseCommand() throws IOException, ConnectionCloseByUserException{
        String commandString = reader.readLine();
        if (commandString == null) {
            return;
        }

        String[] commands = commandString.split(" ");
        switch (commands[0]) {
            case "get":
                getAction(commands);
                break;
            case "set":
                setAction(commands);
                break;
            case "delete":
                deleteAction(commands);
                break;
            case "stats":
                statsAction(commands);
                break;
            case "quit":
                throw new ConnectionCloseByUserException();
            default:
                clientErrorAction();
                System.out.println("COMMAND:" + commands[0] + " NOT IMPLEMENTED.");
        }
    }

    private void getAction(String[] commands) throws IOException{
        if(repository.containsKey(commands[1])){
            String gdata = repository.read(commands[1]);
            writer.write(gdata + "\r\n");
            writer.write("END\r\n");
        }else{
            writer.write("NOT_FOUND\r\n");
        }
        writer.flush();
    }

    private void setAction(String[] commands) throws IOException{
        String sdata = reader.readLine();
        repository.create(commands[1], sdata);
        System.out.println(sdata + " STORED.");
        writer.write("STORED\r\n");
        writer.flush();
    }

    private void deleteAction(String[] commands) throws IOException{
        if(repository.containsKey(commands[1])){
            repository.delete(commands[1]);
            writer.write("DELETED\r\n");
        }else{
            writer.write("NOT_FOUND\r\n");
        }
        writer.flush();
    }

    private void statsAction(String[] commands) throws IOException{

    }

    private void clientErrorAction() throws IOException{
        writer.write("CLIENT_ERROR This command can't use or not implemented.\r\n");
        writer.flush();
    }

}
