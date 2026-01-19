package badWordScanner;

public enum Language {
    EN("English"),
    DE("German");

    private String string;

    Language(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}