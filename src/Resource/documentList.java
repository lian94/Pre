package Resource;

import java.util.ArrayList;

/**
 * This is a class to restore the document list
 */
public class documentList {
    private Document doc;
    private ArrayList<Document> docList;

    /**
     * add current document to the list stored in server
     * @param d current document
     */
    public void addDoc(Document d){
        docList.add(d);
    }

    public void initialDocList(){
        ArrayList<Document> docList = new ArrayList<Document>();
        setResourceList(docList);
    }

    public Document getFirstDoc(){
        return docList.get(0);
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    public ArrayList<Document> getDocList() {
        return docList;
    }
    public void setResourceList(ArrayList<Document> docList) {
        this.docList = docList;
    }
}
