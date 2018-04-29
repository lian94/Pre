package Server;

import Resource.Document;
import java.util.Timer;
import java.util.TimerTask;
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
            Thread autodelete = new Thread(() -> timer(docList, doc.getId()));
            autodelete.start();
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
            throw new Exception("Resource not found");
        }
    }

    public static void timer(HashMap<Integer, Document> docList, int id) {

        System.out.println("New timer scheduled for " + id);

        Timer myTimer = new Timer();
        myTimer.schedule(new AutoDelete(docList, id), 1000 * myServer.TTL);
    }

    static class AutoDelete extends TimerTask {
        private HashMap<Integer, Document> docList;
        private int id;
        public AutoDelete(HashMap<Integer, Document> docList, int id){
            this.docList = docList;
            this.id = id;
        }

        public void run() {
            System.out.println("Start automatic deletion:");
            docList.remove(id);
            System.out.println("Successfully delete document " + id);
        }
    }
}
