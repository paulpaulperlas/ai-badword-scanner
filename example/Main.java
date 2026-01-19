package example;

import java.util.Scanner;
import badWordScanner.BadWordScanner;
import badWordScanner.Response;
import badWordScanner.Sensitivity;

public class Main {
    static String input = "";

    public static void main() {
        System.out.println("Scanner Bereit");
        Scanner scanner = new Scanner(System.in);


        //There are a few Sensitivity: ZERO_TOLERANCE, PROFESSIONAL, STANDARD and MINIMAL
        //You Have to use your own API
        //Is an Example for LM Studios, using the qwen2.5-3b-instruct model (3B is way too weak, it is just for testing!)
        BadWordScanner badWordScanner = new BadWordScanner(Sensitivity.ZERO_TOLERANCE, "http://localhost:1234/v1/chat/completions", "qwen2.5-3b-instruct");

        while (true) {
            input = scanner.nextLine();
            if (input.equals("exit")) break;

            //Gives you a Response Objekt
            Response output = badWordScanner.check(input);

            if (output.isSafe()) {
                System.out.println("-Text ist gut-");
            } else if (!output.isSafe()) {
                System.out.println(output.getMessage());
            }
        }
        scanner.close();
    }
}