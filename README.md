# AI BadWord Scanner (German and English) 

A lightweight Java library that validates **German or English text** using an external AI API. It detects profanity, toxicity, and inappropriate content by analyzing the context, rather than just matching words against a list.

> DE/EN **Note:** This library is currently optimized for the **German and English language**, but it might work with others two. If you want to try use EN.

## Features

* **Context-Aware:** Uses AI to understand if a word is used playfully or offensively.
* **Structured Result:** Returns a simple object containing a status (`isSafe`) and a specific reason.
* **BYO-API:** Designed to work with your own API Key (Privacy & Control).
* **Native Java:** Easy to drop into any Java project.
* **Smart Caching System:** Integrated, memory-efficient cache that responds to repeated requests immediately without calling the AI. This saves API costs and reduces latency.
* **Intelligentes Ressourcen-Management:** Only stores relevant short texts (less than 50 characters) and automatically deletes the longest unused entries (LRU) to keep RAM            consumption to a minimum.

## Installation

Since this is a lightweight library, you can currently install it by adding the source files directly to your project:

1.  Download the `badWordScanner` folder from this repository.
2.  Copy the package into your Java project's source directory.

## SetUp

I woud Recoment LM Studio, its the simlest version, and has a good GUI, however you can also use a API 

## Usage

Here is an Example how to use the scanner in your application. (You can also find in the folder `example`)


```java
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
        BadWordScanner badWordScanner = new BadWordScanner(Sensitivity.ZERO_TOLERANCE, Language.EN, "http://localhost:1234/v1/chat/completions", "qwen2.5-3b-instruct");

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
