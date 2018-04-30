package Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class clientObject {

    private Socket s = null;

    /**
     * Start client socket bound to a specific server
     * @param serverIP
     * @param serverPort
     */
    public clientObject(String serverIP, int serverPort) {
        try {
            s = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * write JSONObject to server end and receive input from server
     * @param j user input JSONObject
     * @throws JSONException
     */
    public void sendJSON(JSONObject j) throws JSONException {
        try {
            DataInputStream input = new DataInputStream(s.getInputStream());
            DataOutputStream output = new DataOutputStream(s.getOutputStream());

            output.writeUTF(j.toString());
            output.flush();

            while(true){
                String response;
                if(input.available() > 0){
                    //System.out.println("input accepted");
                    response = input.readUTF();
                    System.out.println(response);
                    break;
                }
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

