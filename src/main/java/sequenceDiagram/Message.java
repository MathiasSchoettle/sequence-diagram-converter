package sequenceDiagram;

public class Message {
    public String source;
    public String target;
    public MessageType type;

    private enum MessageType {
        ASYNCHRONOUS, SYNCHRONOUS
    }
}
