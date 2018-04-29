package Server;

import Resource.Document;
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
    public static void post(JSONObject request, DataOutputStream out, HashMap<Integer, Document> docList)
    throws IOException{
        JSONObject reply = new JSONObject();
        boolean response;
        if(request.has("document")){
            try{
                JSONObject docInfo = request.getJSONObject("document");
                int id = docInfo.getInt("id");
                String msg = docInfo.getString("message");
                Document doc = new Document(id, msg);
                response = Function.post(doc, docList);
                System.out.println("receive doc: " + id + msg);
                if(response){
                    reply.put("response", "success");
                }
                else{
                    reply.put("response", "error");
                    reply.put("errorMessage", "Document id already existed");
                }
            }catch(JsonSyntaxException j){
                reply.put("response", "error");
                reply.put("errorMessage", "missing resource");
            }
        }else{
            reply.put("response", "error");
            reply.put("errorMessage", "missing resource");
        }
        out.writeUTF(reply.toString());
    }

    public static void get(JSONObject request, DataOutputStream out, HashMap<Integer, Document> docList)
            throws IOException{
        JSONObject reply = new JSONObject();
        String response;
        if(request.has("document")){
            try{
                JSONObject docInfo = request.getJSONObject("document");
                int id = docInfo.getInt("id");
                String msg = "";
                try {
                    response = Function.get(id, docList);
                    reply.put("response", "success");
                    reply.put("message", response);
                } catch(Exception e){
                    reply.put("response", "error");
                    reply.put("errorMessage", e.getMessage());
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
