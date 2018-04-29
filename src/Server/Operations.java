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
        if(request.has("POST")){
            try{
                int id = request.getInt("id");
                String msg = request.getString("message");
                Document doc = new Document(id, msg);
                response = Function.get(doc, docList);
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
}
