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

    private boolean useCache;
    private int maxCacheSize; //Recommend 10000
    private int maxCachedWordLength;//Recommend 25 - 50
    private Map<String, Response> cache = Collections.synchronizedMap( //Cache so more often used words are faster and don't need AI
            new LinkedHashMap<String, Response>(1000, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Response> eldest) {
                    return size() > maxCacheSize;
                }
            }
    );

    private double temperature = 0.01;
    private double top_p = 0.1;
    private int max_tokens = 30;
    private String user = "BadWordScanner";

    // Automatically deactivates cache
    public BadWordScanner(Sensitivity sensitivity, Language language, String api_url, String ai_model) {
        this.sensitivity = sensitivity;
        this.language = language;
        this.apiUrl = api_url;
        this.aiModel = ai_model;
        useCache = false;
    }

    // Automatically activates cache
    public BadWordScanner(Sensitivity sensitivity, Language language, String api_url, String ai_model, int maxCacheSize, int maxCachedWordLength) {
        this.sensitivity = sensitivity;
        this.language = language;
        this.apiUrl = api_url;
        this.aiModel = ai_model;
        useCache = true;
        this.maxCacheSize = maxCacheSize;
        this.maxCachedWordLength = maxCachedWordLength;
    }

    public Response check(String text) {
        Response response;
        String cachKey = text.strip().toLowerCase();

        if (sensitivity == Sensitivity.NOFILTER) { //NOFILTER skips AI
            return new Response(true, "");
        } else if (useCache && cache.containsKey(cachKey)) {
            response = cache.get(cachKey);
        } else {
            response = createChecked(checkMessage(text));
        }
        if (useCache) manageCache(cachKey, response);
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
        if (cache.size() <= maxCachedWordLength && !response.getMessage().contains("[error]")) {
            String cleanMessage = message.strip();

            cache.put(cleanMessage.toLowerCase(), response);
        }
    }

    public void clearCach() {
        if (useCache) cache.clear();
    }



    public Sensitivity getSensitivity() {
        return sensitivity;
    }
    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
        if (useCache) cache.clear();
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

    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
    public boolean isUseCache() {
        return useCache;
    }
    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }
    public int getMaxCacheSize() {
        return maxCacheSize;
    }
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
    public int getMaxCachedWordLength() {
        return maxCachedWordLength;
    }
    public void setMaxCachedWordLength(int maxCachedWordLength) {
        this.maxCachedWordLength = maxCachedWordLength;
    }
    public Map<String, Response> getCache() {
        return cache;
    }
    public void setCache(Map<String, Response> cache) {
        this.cache = cache;
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