package Resource;

import java.util.HashMap;

/**
 * This is a class to restore a single document
 * that the client post to the server
 */
public class Document {

    private int id;
    private String message;

    public Document(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, String > docToJSON(){
        HashMap<String , String> map = new HashMap<String, String>();
        map.put("id",this.getId().toString());
        map.put("message",this.getMessage());
        return map;
    }


}
