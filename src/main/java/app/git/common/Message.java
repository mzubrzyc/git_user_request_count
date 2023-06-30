package app.git.common;

public class Message {

    private Object o;

    public Message(Object o) {
        this.o = o;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    @Override
    public String toString() {
        return "Message{" +
               "o=" + o +
               '}';
    }
}
