package com.leafgraph.tshimizu.sysdev.memcached.domain;

import com.leafgraph.tshimizu.sysdev.memcached.infra.DataRepository;
import com.leafgraph.tshimizu.sysdev.memcached.infra.ProccessCounter;
import com.leafgraph.tshimizu.sysdev.memcached.util.CRLFBufferedReader;
import com.leafgraph.tshimizu.sysdev.memcached.util.ConnectionCloseByUserException;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by takahiro on 2016/11/02.
 */
public class ServerUseCase{
    private CRLFBufferedReader reader;
    private BufferedWriter writer;
    private DataRepository repository = DataRepository.getSingleton();
    private ProccessCounter counter = ProccessCounter.getSingleton();

    public void initProtocolIO(CRLFBufferedReader reader, BufferedWriter writer) {
        this.reader=reader;
        this.writer=writer;
    }

    public void parseCommand() throws IOException, ConnectionCloseByUserException{
        String commandString = reader.readLine();
        if (commandString == null||commandString.equals("")) {
            return;
        }

        String[] commands = commandString.split(" ");
        // TODO: 本当はコマンドごとにUseCaseをクラスとして分けるべきですが、少し冗長なので見送り･･･
        switch (commands[0].toLowerCase()) {
            case "get":
                counter.increment_cmd_get();
                getAction(commands);
                break;
            case "set":
                counter.increment_cmd_set();
                setAction(commands);
                break;
            case "delete":
                deleteAction(commands);
                break;
            case "stats":
                statsAction(commands);
                break;
            case "flush":
                flushAction(commands);
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
            System.out.println("GET "+commands[1]);
            writer.write(gdata + "\r\n");
            writer.write("END\r\n");
            counter.increment_get_hits();
        }else{
            System.out.println("NOT_FOUND");
            writer.write("NOT_FOUND\r\n");
            counter.increment_get_misses();
        }
        writer.flush();
    }

    private void setAction(String[] commands) throws IOException{
        String sdata = reader.readLine();
        repository.create(commands[1], sdata);
        System.out.println("STORED :"+sdata);
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
        writer.write("STAT curr_items "+repository.countItems()+"\r\n");
        writer.write("STAT total_items "+repository.countItems()+"\r\n");
        writer.write("STAT cmd_get "+counter.getCmd_get()+"\r\n");
        writer.write("STAT cmd_set "+counter.getCmd_set()+"\r\n");
        writer.write("STAT get_hits "+counter.getGet_hits()+"\r\n");
        writer.write("STAT get_misses "+counter.getGet_misses()+"\r\n");

        writer.write("END\r\n");
        writer.flush();
    }

    private void flushAction(String[] commands) throws IOException{
        repository.flushData();
        writer.write("ACK\r\n");
        writer.flush();
    }

    private void clientErrorAction() throws IOException{
        writer.write("CLIENT_ERROR This command can't use or not implemented.\r\n");
        writer.flush();
    }

}
