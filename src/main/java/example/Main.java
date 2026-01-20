package example;

import java.util.Scanner;
import badWordScanner.BadWordScanner;
import badWordScanner.Language;
import badWordScanner.Response;
import badWordScanner.Sensitivity;

public class Main {
    static String input = "";

    public static void main(String[] args) {
        System.out.println("Scanner Ready");
        Scanner scanner = new Scanner(System.in);

        //There are a few Sensitivity: ZERO_TOLERANCE, PROFESSIONAL, STANDARD and MINIMAL
        //There are a few Languages: German: DE, English: EN
        //You Have to use your own API
        //Is an Example for LM Studio, using the qwen2.5-3b-instruct model (3B is probably a little weak, but enough)
        //If you don't specify a max cache size and max cached word length it automatically deactivates caching
        BadWordScanner badWordScanner = new BadWordScanner(Sensitivity.ZERO_TOLERANCE, Language.EN, "http://localhost:1234/v1/chat/completions", "qwen2.5-3b-instruct", 10000, 25);

        while (true) {
            input = scanner.nextLine();
            if (input.equals("exit")) break;

            //Gives you a Response Objekt
            Response output = badWordScanner.check(input);

            //isSave()  function tells you whether the text contains any bad words
            //true = the text is safe
            //false = the text is not safe
            if (output.isSafe()) {
                System.out.println("-Text is fine-");
            } else if (!output.isSafe()) {
                //getMessage gives you the reasoning behind why the text is not safe, explained by the AI
                System.out.println(output.getMessage());
            }
        }
        scanner.close();
    }
}