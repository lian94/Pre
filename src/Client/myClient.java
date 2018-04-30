package Client;

import org.json.JSONException;
import org.json.JSONObject;

public class myClient {
    public static void main(String[] args) throws JSONException {

        //This is the default host host and port number
        Integer serverPort = 20006;
        String serverIP = "localhost";

        String action = args[0];

        clientObject c = new clientObject(serverIP, serverPort);
        System.out.println("client object created");

        if(action.equals("POST")){
            String folder = args[1];
            JSONObject docInfo = new JSONObject(args[2]);
            try {
                Operations.Post(docInfo, c);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        if(action.equals("GET")){
            try {
                String docInfo = args[1];
                int split = docInfo.lastIndexOf('/');
                String folder = docInfo.substring(0,split+1);
                Operations.Get(Integer.parseInt(docInfo.substring(split+1)), c);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
