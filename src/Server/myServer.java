package Server;

import Resource.Document;
import com.google.gson.JsonParseException;
import org.apache.commons.cli.*;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

public class myServer extends Thread{

    // initial parameters
    private ServerSocket server;
    private String hostname = "localhost";
    private int port  = 20006;
    protected static int TTL = 30;
    private Socket socket = null;
    private enum Operation {POST, GET, CLEAR;}
    public HashMap<Integer, Document> documentList = new HashMap<Integer, Document>();

    public myServer(String[] args){
        try {
            initialize(args);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args)throws Exception{
        myServer server = new myServer(args);
        server.start();
    }

    public void initialize(String[] args)throws Exception {

        Options options = new Options();

        options.addOption("port", true, "server port, an integer");
        options.addOption("TTL", true, "Time survival of document, an integer");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("port")) {
            setPort(Integer.parseInt(cmd.getOptionValue("port")));
        }
        if (cmd.hasOption("TTL")) {
            setTTL(Integer.parseInt(cmd.getOptionValue("TTL")));
        }

        server = new ServerSocket(getPort());

    }
    public void run(){
        System.out.println("waiting");
        while (true) {
            try {
                //wait for connection
                socket = server.accept();
                System.out.println("New connection established!");
                Thread newthread = new Thread(() -> {
                    try {
                        handler(socket);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                newthread.start();

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public void handler(Socket client) throws IOException, ParseException{
        try {
            if (!client.isClosed()) {

                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream buf = new DataInputStream(client.getInputStream());

                try {
                    String str = buf.readUTF();
                    parseJson(str, out);
                } catch (IOException i) {
                    // TODO: handle exception
                } finally {
                    out.close();
                    client.close();
                }
            }
        }catch(Exception e){
            try {
                client.close();
            } catch (IOException e1) {

            }
        }

    }

    private void parseJson(String message, DataOutputStream out){
        JSONObject response = new JSONObject();
        try{
            JSONObject root = new JSONObject(message);

            if(root.has("command")){
                String commandName = root.get("command").toString();
                Operation operation = Operation.valueOf(commandName);
                System.out.println("this is command" + commandName);
                //check command and call corresponding method
                switch(operation){
                    case POST:{
                        //check resource field exists
                        Operations.post(root, out, documentList);
                        break;
                    }
                    case GET:{
                        //check resource field exists
                        Operations.get(root, out, documentList);
                        System.out.println("get succeed");
                        break;
                    }
                    default:{
                        response.put("response", "error");
                        response.put("errorMessage", "invalid command");
                        out.writeUTF(response.toString());
                        break;
                    }
                }
            }else{
                response.put("response", "error");
                response.put("errorMessage", "missing or incorrect type for command");
                try {
                    out.writeUTF(response.toString());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                }
            }
        }catch (JsonParseException e) {
            // TODO: handle exception
            response.put("response", "error");
            response.put("errorMessage", "missing or incorrect type for command");
            try {
                out.writeUTF(response.toString());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }catch (Exception e){
            response.put("response", "error");
            response.put("errorMessage", "missing or incorrect type for command");
            try {
                out.writeUTF(response.toString());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }
}
