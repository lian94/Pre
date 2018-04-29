package Client;

import org.apache.commons.cli.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Operations {

    /**
     * Decide whether document is valid
     * 1. id cannot be empty
     * 2. id must be unique
     */

    private static String getId(CommandLine cmd) {
        String id = "";
        if (cmd.hasOption("id")) {
            id = cmd.getOptionValue("id");
        }
        return id;
    }

    private static String getMsg(CommandLine cmd) {
        String msg = "";
        if (cmd.hasOption("message")) {
            msg = cmd.getOptionValue("message");
        }
        return msg;
    }

    private static JSONObject getDocument(CommandLine cmd) throws JSONException {
        JSONObject doc = new JSONObject("{}");
        String id = getId(cmd);
        String msg = getMsg(cmd);
        doc.put("id", id);
        doc.put("message", msg);
        return doc;
    }

    public static void Post(CommandLine cmd, clientObject c) throws JSONException {

        JSONObject sentJSON = new JSONObject("{}");
        JSONObject doc = getDocument(cmd);

        sentJSON.put("command", "POST");
        sentJSON.put("document", doc);
        c.sendJSON(sentJSON, cmd.getOptionValue("host"), cmd.getOptionValue("port"));

    }

}
