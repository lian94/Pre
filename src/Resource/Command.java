package Resource;

public class Command {
    private String command;
    private Document doc;

    public Command(String command, Document doc){
        this.command = command;
        this.doc = doc;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Document getResource() {
        return doc;
    }

    public void setResource(Document resource) {
        this.doc = resource;
    }
}
