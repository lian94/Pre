package Server;

import Resource.Document;
import Resource.documentList;

import java.util.HashMap;

public class Function {

    /**
     * @param doc Document info in JSON format
     * @param docList server maintained docList
     * @return success or error information
     */
    public static HashMap<Boolean, String> post(Document doc, documentList docList){
        HashMap<Boolean, String> toReturn = new HashMap<Boolean, String>();
        for (Document d : docList.getDocList()) {
            if (d.getId() == doc.getId()) {
                //If the id existed, sever will deny the request
                //Error message will be given
                toReturn.put(false, "Document with same id already existed");
                return toReturn;
            }
        }
        docList.addDoc(doc);
        toReturn.put(true, "success");
        return toReturn;
    }

    /**
     *
     * @param doc Document info in JSON format
     * @param docList server maintained docList
     * @return success or error information
     */
    public static HashMap<Boolean, String> get(Document doc, documentList docList){
        HashMap<Boolean, String> toReturn = new HashMap<Boolean, String>();
        toReturn.put(true, null);

        for(Document d: docList.getDocList()){
            if (d.getId() == doc.getId()){
                toReturn.put(true, d.getMessage());
                return toReturn;
            }
        }
        toReturn.put(false, "Document not found!");
        System.out.println(toReturn);
        return toReturn;
    }
}
