# AI BadWord Scanner (German) 

A lightweight Java library that validates **German text** using an external AI API. It detects profanity, toxicity, and inappropriate content by analyzing the context, rather than just matching words against a list.

> 🇩🇪 **Note:** This library is currently optimized for the **German language**.

## Features

* **Context-Aware:** Uses AI to understand if a word is used playfully or offensively.
* **Structured Result:** Returns a simple object containing a status (`isSafe`) and a specific reason.
* **BYO-API:** Designed to work with your own API Key (Privacy & Control).
* **Native Java:** Easy to drop into any Java project.

## Installation

Since this is a lightweight library, you can currently install it by adding the source files directly to your project:

1.  Download the `badWordScanner` folder from this repository.
2.  Copy the package into your Java project's source directory.

## Usage

Here is an Example you use the scanner in your application. (You can also find in the folder `example`


```java
package example;

import java.util.Scanner;
import badWordScanner.BadWordScanner;
import badWordScanner.Response;
import badWordScanner.Sensitivity;

public class Main {
    static String input = "";

    public static void main(String[] args) {
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
