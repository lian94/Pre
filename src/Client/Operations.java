package Client;

import org.json.JSONException;
import org.json.JSONObject;

public class Operations {


    public static void Post(JSONObject docInfo, clientObject c) throws JSONException {
        JSONObject sentJSON = new JSONObject("{}");
        sentJSON.put("command", "POST");
        sentJSON.put("document", docInfo);
        c.sendJSON(sentJSON);
        //System.out.println("sent post json");
    }

    public static void Get(int dId, clientObject c) throws JSONException {
        JSONObject sentJSON = new JSONObject("{}");
        JSONObject doc = new JSONObject("{}");
        doc.put("id", dId);
        doc.put("message", "");
        sentJSON.put("command", "GET");
        sentJSON.put("document", doc);
        c.sendJSON(sentJSON);
        //System.out.println(sentJSON);
        //System.out.println("sent get json");
    }

}
