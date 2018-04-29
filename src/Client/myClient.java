package Client;

import org.apache.commons.cli.*;
import org.json.JSONException;

public class myClient {
    public static void main(String[] args) throws JSONException {

        //This is the default host host and port number
        Integer serverPort = 20006;
        String serverIP = "localhost";

        Options option = new Options();
        option.addOption("id", true, "document id");
        option.addOption("message", true, "document message");
        option.addOption("POST", false, "post document");
        option.addOption("GET", false, "get document");
        option.addOption("host", true, "server host");
        option.addOption("port", true, "server port, an integer");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(option, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        if (cmd.hasOption("host")) {
            serverIP = cmd.getOptionValue("host");
        }
        if (cmd.hasOption("port")) {
            serverPort = Integer.parseInt(cmd.getOptionValue("port"));
        }

        clientObject c = new clientObject(serverIP, serverPort);
        System.out.println("client object created");
        System.out.println(cmd.hasOption("POST"));

        if (cmd.hasOption("POST")) {
            //System.out.println("post");
            try {
                Operations.Post(cmd, c);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        if (cmd.hasOption("GET")) {
            //System.out.println("get");
            try {
                Operations.Get(cmd, c);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
