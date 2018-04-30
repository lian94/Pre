package Server;

import Resource.Document;
import com.google.gson.JsonParseException;
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args)throws Exception{
        myServer server = new myServer(args);
        server.start();
    }

    /**
     * set the ttl and create a server socket
     * @param args input from server side
     */
    public void initialize(String[] args)throws Exception {
        try{
            if(args.length != 0){
                if(args[0].equalsIgnoreCase("TTL")){
                    setTTL(Integer.parseInt(args[1]));
                }else{
                    System.out.println("Invalid server command!");
                    System.exit(-1);
                }
            }
        }catch(Exception e){
            e.getStackTrace();
        }
        server = new ServerSocket(getPort());
    }

    /**
     * create thread pool to deal with multiple connections and requests
     */
    public void run(){
        System.out.println("waiting for a client to connect");
        while (true) {
            try {
                //wait for connection
                socket = server.accept();
                System.out.println("New connection established!");
                Thread newthread = new Thread(() -> {
                    try {
                        handler(socket);
                    } catch (Exception e) {
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

    /**
     * get the connection and read input then parse it to a function to receive output
     * @param client client socket connection
     */
    public void handler(Socket client) throws IOException, ParseException{
        try {
            if (!client.isClosed()) {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream buf = new DataInputStream(client.getInputStream());
                try {
                    String str = buf.readUTF();
                    parseJson(str, out);
                } catch (IOException i) {

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

    /**
     * receive the input from client and handle operations
     * then return the result to client
     * @param message in stream from client
     * @param out out stream to client
     */
    private void parseJson(String message, DataOutputStream out){
        JSONObject response = new JSONObject();
        try{
            JSONObject root = new JSONObject(message);

            if(root.has("command")){
                String commandName = root.get("command").toString();
                Operation operation = Operation.valueOf(commandName);
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
                        break;
                    }
                    default:{
                        response.put("error", "400");
                        response.put("errorMessage", "invalid command");
                        out.writeUTF(response.toString());
                        break;
                    }
                }
            }else{
                response.put("error", "400");
                response.put("errorMessage", "missing or incorrect type for command");
                try {
                    out.writeUTF(response.toString());
                } catch (IOException e1) {

                }
            }
        }catch (JsonParseException e) {

            response.put("error", "400");
            response.put("errorMessage", "missing or incorrect type for command");
            try {
                out.writeUTF(response.toString());
            } catch (IOException e1) {

            }
        }catch (Exception e){
            response.put("error", "400");
            response.put("errorMessage", "missing or incorrect type for command");
            try {
                out.writeUTF(response.toString());
            } catch (IOException e1) {

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
