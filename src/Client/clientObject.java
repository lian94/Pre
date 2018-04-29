package Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class clientObject {

    private Socket s = null;

    public clientObject(String serverIP, int serverPort) {
        try {
            s = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendJSON(JSONObject j, String host, String port) throws JSONException {
        try {
            DataInputStream input = new DataInputStream(s.getInputStream());
            DataOutputStream output = new DataOutputStream(s.getOutputStream());

            output.writeUTF(j.toString());
            output.flush();

            String response = "";
            if(input.available() > 0){
                response = input.readUTF();
                System.out.println(response);
            }

        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

}

