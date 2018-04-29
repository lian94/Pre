package Server;

import Resource.documentList;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import org.json.JSONObject;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class Server implements Runnable{

    private enum Operation {POST, GET, CLEAR;}

    private Socket client;
    private String hostname;
    private int port;
    private documentList docList;
    private int ttl;

    public Server(Socket client, documentList docList, String hostname, int port, int TTL){
        this.client = client ;
        this.docList = docList;
        this.ttl = TTL;
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        try {
            if (!client.isClosed()) {

                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream buf = new DataInputStream(client.getInputStream());
                JsonReader request = new JsonReader(new InputStreamReader(client.getInputStream()));

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
                //check command and call corresponding method
                switch(operation){
                    case POST:{
                        //check resource field exists
                        Operations.post(root, out, docList);
                        break;
                    }
                    default:{
                        response.put("response", "error");
                        response.put("errorMessage", "invalid Command");
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
        return ttl;
    }

    public void setTTL(int ttl) {
        this.ttl = ttl;
    }
}
