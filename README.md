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

1. Download the src/main/java/badWordScanner folder from this repository.
2. Copy the package into your Java project's source directory (e.g., src/main/java/).

## SetUp

I would recommend LM Studio, it's the simplest version and has a good GUI. However, you can also use any other API compliant server.

1. Start by downloading LM Studio from the [official website](https://lmstudio.ai/download).
2. Follow the steps for installation.
3. Once in the software, go to the Search (magnifying glass) on the left and choose an AI you want to use.
4. Download the AI and use CTRL + L to load your model into memory.
5. Go to the Developer tab (bracket icon <>) on the left and toggle Start Server.

## AI Requirements

**Minimum**
* 3B Model
* 1K Context
* 15 - 30 Tokens/s

**Recommended**
* 8B - 14BModel
* 2k Context
* 30 - 50 Tokens/s


## Recommend models

* **[Qwen2.5-3B-Instruct](https://model.lmstudio.ai/download/lmstudio-community/Qwen2.5-3B-Instruct-GGUF)** Lightweight but bare minimum, also Runs on ancient hardware.
* **[Llama-3-8B-Instruct](https://model.lmstudio.ai/download/crusoeai/dolphin-2.9.1-llama-3-8b-GGUF)** (Recommended). This is a modified version of Llama 3 that removes the strict safety filters, making it perfect for moderation tasks.
* **[Mistral-Nemo-12B-Instruct](https://model.lmstudio.ai/download/dphn/dolphin-2.9.3-mistral-nemo-12b-gguf)** The perfect balance of speed and intelligence. Fits easily into 12GB VRAM or less. (If you're ok with using french things.)
* **[Qwen2.5-14B-Instruct](https://model.lmstudio.ai/download/bartowski/Qwen2.5-14B-Instruct-GGUF)** (Recommended for Stronger hardware) My personal daily driver. Extremely smart and follows instructions perfectly.
* **[Qwen2.5-32B-Instruct](https://model.lmstudio.ai/download/lmstudio-community/Qwen2.5-32B-Instruct-GGUF)** Overkill, Your Users get really creative hiding Slurs? Might be time for this. 

## Recommend Hardware

Based on what you are using the Scanner for and which model you choose, your recommended hardware might differ. 
I would recommend trying out the models and looking at the Tokens/s to get an idea of what you need. Look at these 
example cases. If your Tokens/s is lower than these, I would recommend using a smaller model or upgrading your 
hardware.

* **For a small friend chat** You need about 15 - 30 Tokens/s
* **A Small Minecraft server** You need about 30 - 45Tokens/s
* **Active Community / Forum** You need about 50 - 80 Tokens/s
* **Giant Chat with thousands of users** You need about 90 - 120 Tokens/s

## Usage

Here is an Example how to use the scanner in your application. (You can also find in the folder `src/main/java/example`)

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
