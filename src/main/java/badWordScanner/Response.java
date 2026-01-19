package badWordScanner;

public class Response {
    private boolean isSafe; //true = the messag is safe | false = The message isn't safe
    private String message; //if the message isn't safe, the AI Adds a Reason

    public Response(boolean isSafe, String message) {
        this.isSafe = isSafe;
        this.message = message;
    }

    public boolean isSafe() {
        return isSafe;
    }
    public void setSafe(boolean safe) {
        isSafe = safe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}