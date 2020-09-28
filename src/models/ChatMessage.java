package models;

public class ChatMessage {
    public String from;
    public String to;
    public String message;
    public boolean isMe = false;

    public ChatMessage(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    @Override
    public String toString() {
        return from+": "+message;
    }
}
