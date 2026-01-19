package badWordScanner;

public class Response {
    private boolean isSafe;
    private String message;

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
