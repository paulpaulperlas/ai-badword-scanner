package badWordScanner;

import badWordScanner.helper.HttpHelper;

import java.net.http.HttpResponse;
import java.util.*;

import static badWordScanner.helper.JsonHelper.*;

public class BadWordScanner {
    private Sensitivity sensitivity; //There are a few Sensitivity: ZERO_TOLERANCE, PROFESSIONAL, STANDARD and MINIMAL
    private Language language; //There are German (DE) and Englisch (EN)
    private String apiUrl;
    private String aiModel;

    private final int MAX_CACHE_SIZE = 10000;
    private final int MAX_CACHED_WORD_LENGTH = 50;
    private Map<String, Response> cache = Collections.synchronizedMap( //Cach so more often used words are faster and don't need AI
            new LinkedHashMap<String, Response>(1000, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Response> eldest) {
                    return size() > MAX_CACHE_SIZE;
                }
            }
    );

    private double temperature = 0.01;
    private double top_p = 0.1;
    private int max_tokens = 30;
    private String user = "BadWordScanner";


    public BadWordScanner(Sensitivity sensitivity, Language language, String api_url, String ai_model) {
        this.sensitivity = sensitivity;
        this.language = language;
        this.apiUrl = api_url;
        this.aiModel = ai_model;
    }

    public Response check(String text) {
        Response response;
        String cachKey = text.strip().toLowerCase();

        if (sensitivity == Sensitivity.NOFILTER) { //NOFILTER skips AI
            return new Response(true, "");
        } else if (cache.containsKey(cachKey)) {
            response = cache.get(cachKey);
        } else {
            response = createChecked(checkMessage(text));
        }
        manageCache(cachKey, response);
        return response;
    }


    public String checkMessage(String text) {
        try {
            String safeMessage = makeSafeForJson(text);

            String systemprompt = "You are a moderator. Check the following " + language.getString() + " text. " +
                    "**Rules: **" +
                    "1. If the text does NOT meet the conditions: Respond ONLY with: [false] " +
                    "2. If the text meets the conditions (including hidden ones such as Leetspeak, e.g., ‘3’ instead of ‘e’): Respond with: [true] - followed by the words that meet the conditions and a brief explanation of why you recognized a word (max. 1 sentence). " +
                    "3. Send [true] or [false] first, nothing else!!! The first thing you send must be one of these!" +
                    "4. This also applies if the word is hidden, i.e. letters have been swapped or reversed. " +

                    "Conditions that should be [true]:" +
                    sensitivity.getConditions() +

                    "Exceptions that should be [false]:" +
                    sensitivity.getExceptions();

            String safeSystemPrompt = makeSafeForJson(systemprompt);

            String[][] example;

            switch (language) {
                case DE -> example = sensitivity.getExampleDE();
                default -> example = sensitivity.getExampleEN();
            }

            String jsonBody = "{\n" +
                    "  \"model\": \"" + aiModel + "\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"system\", \"content\": \"" + safeSystemPrompt + "\"},\n" +

                    //Adds Examples as Context for the AI
                    makeContextForJson(example)  +


                    //Adds new message for the AI to Check
                    "    {\"role\": \"user\", \"content\": \"" + safeMessage + "\"}\n" +
                    "  ],\n" +

                    //Gives AI the Parameters
                    "  \"temperature\": " + temperature + ",\n" +
                    "  \"top_p\": " + top_p + ",\n" +
                    "  \"max_tokens\": " + max_tokens + ",\n" +
                    "  \"user\": \"" + user + "\"" +
                    "}";


            HttpResponse<String> response = HttpHelper.sendHttpRequest(jsonBody, apiUrl);

            if (response.statusCode() == 200) {
                return extractTextFromJSON(response.body());
            } else {
                return "[error] Server response with Code: " + response.statusCode();
            }

        } catch (Exception e) {
            return "[error] connection problem";
        }
    }

    private Response createChecked(String response) {
        String stripedResponse = response.strip();
        String lowerResponse = stripedResponse.toLowerCase();

        //Creates a Response Objekt, based on The AIs response (true and false is switched
        if (lowerResponse.contains("[false]") || lowerResponse.contains("false")) {
            return new Response(true, "");
        } else if (lowerResponse.contains("[true]") || lowerResponse.contains("true")) {
            return new Response(false, response.replace("[true]", ""));
        } else if (lowerResponse.startsWith("[error]")) {
            return new Response(false, response);
        } else {
            return new Response(false, "[error] [AI Problem:] " + response);
        }
    }

    private void manageCache(String message, Response response) {
        if (cache.size() <= MAX_CACHED_WORD_LENGTH && !response.getMessage().contains("[error]")) {
            String cleanMessage = message.strip();

            cache.put(cleanMessage.toLowerCase(), response);
        }
    }

    public void clearCach() {
        cache.clear();
    }


    public Sensitivity getSensitivity() {

        return sensitivity;
    }
    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
        cache.clear();
    }
    public String getApiUrl() {
        return apiUrl;
    }
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    public String getAiModel() {
        return aiModel;
    }
    public void setAiModel(String aiModel) {
        this.aiModel = aiModel;
    }


    //You Probably won't need this
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        cache.clear();
    }
    public double getTop_p() {
        return top_p;
    }
    public void setTop_p(double top_p) {
        this.top_p = top_p;
        cache.clear();
    }
    public int getMax_tokens() {
        return max_tokens;
    }
    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
        cache.clear();
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
        cache.clear();
    }
}
