package Server;

import Resource.documentList;
import org.apache.commons.cli.*;

import java.net.ServerSocket;
import java.net.Socket;

public class myServer {

    // initial parameters
    private String hostname = "localhost";
    private int port  = 20006;
    private int TTL = 30;
    private Resource.documentList docList;

    public static void main(String[] args)throws Exception{
        myServer server = new myServer();
        server.initialize(args);
    }

    public  void initialize(String[] args)throws Exception {
        //set default parameters
        docList = new documentList();
        docList.initialDocList();
        Options options = new Options();

        options.addOption("port", true, "server port, an integer");
        options.addOption("TTL", false, "Time survival of document, an integer");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("port")) {
            setPort(Integer.parseInt(cmd.getOptionValue("port")));
        }
        if (cmd.hasOption("TTL")) {
                setTTL(Integer.parseInt(cmd.getOptionValue("TTL")));
        }

        ServerSocket server = new ServerSocket(getPort());
        System.out.println("wating");

        Socket client = server.accept();
        System.out.println("connection succeed !");
        if (client.isConnected()) {
            Server s = new Server(client, docList, getHostname(), getPort(), getTTL());
            s.run();
        } else {
            System.out.println("exit");
            client.close();
        }
        //server.close();

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
