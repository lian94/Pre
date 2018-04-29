package Server;

import Resource.Document;

import java.util.HashMap;

public class Function {

    /**
     * @param doc Document info in JSON format
     * @param docList server maintained docList
     * @return success or error information
     */
    public static boolean post(Document doc, HashMap<Integer, Document> docList){

        if(docList.containsKey(doc.getId())){
            return false;
        }else{
            docList.put(doc.getId(), doc);
            return true;
        }
    }

    /**
     *
     * @param id Document id
     * @param docList server maintained docList
     * @return success or error information
     */
    public static String get(Integer id,  HashMap<Integer, Document> docList) throws Exception{
        if(docList.containsKey(id)){
            return docList.get(id).getMessage();
        }else{
            throw new Exception("Document not found");
        }
    }
}
