package Server;

import Resource.Document;
import Resource.documentList;
import com.google.gson.JsonSyntaxException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Operations {
    /**
     *
     * @param request Request from the client
     * @param out Response to client
     * @param docList DocList stored on server side
     * @throws IOException
     */
    public static void post(JSONObject request, DataOutputStream out, documentList docList)
    throws IOException{
        JSONObject reply = new JSONObject();
        HashMap<Boolean, String> response = new HashMap<Boolean, String>();
        if(request.has("document")){
            try{
                JSONObject docInfo = request.getJSONObject("document");
                int id = docInfo.getInt("id");
                String msg = docInfo.getString("message");
                Document doc = new Document(id, msg);
                response = Function.post(doc, docList);
                System.out.println("receive doc: " + id + msg);
                if(response.containsKey(true)){
                    reply.put("response", "success");
                }
                else{
                    reply.put("response", "error");
                    reply.put("errorMessage", response.get(false));
                }
            }catch(JsonSyntaxException j){
                reply.put("response", "error");
                reply.put("errorMessage", "missing document");
            }

        }else{
            reply.put("response", "error");
            reply.put("errorMessage", "missing document");
        }
        out.writeUTF(reply.toString());
    }

    public static void get(JSONObject request, DataOutputStream out, documentList docList)
            throws IOException{
        JSONObject reply = new JSONObject();
        HashMap<Boolean, String> response = new HashMap<Boolean, String>();
        if(request.has("document")){
            try{
                JSONObject docInfo = request.getJSONObject("document");
                int id = docInfo.getInt("id");
                String msg = "";
                Document doc = new Document(id, msg);
                response = Function.get(doc, docList);
                System.out.println("receive doc: " + id + msg);
                if(response.containsKey(true)){
                    reply.put("response", "success");
                    reply.put("message", response.get(true));
                }
                else{
                    reply.put("response", "error");
                    reply.put("errorMessage", response.get(false));
                }
            }catch(JsonSyntaxException j){
                reply.put("response", "error");
                reply.put("errorMessage", "missing document");
            }

        }else{
            reply.put("response", "error");
            reply.put("errorMessage", "missing document");
        }
        out.writeUTF(reply.toString());
    }
}
