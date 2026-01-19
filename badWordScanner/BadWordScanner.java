package badWordScanner;

import badWordScanner.helper.HttpHelper;

import java.net.http.HttpResponse;

import static badWordScanner.helper.JsonHelper.*;

public class BadWordScanner {
    private Sensitivity sensitivity;
    private String apiUrl;
    private String aiModel;

    private double temperature = 0.01;
    private double top_p = 0.1;
    private int max_tokens = 30;
    private String user = "BadWordScanner";


    public BadWordScanner(Sensitivity sensitivity, String api_url, String ai_model) {
        this.sensitivity = sensitivity;
        this.apiUrl = api_url;
        this.aiModel = ai_model;
    }

    public Response check(String text) {
        if (sensitivity == Sensitivity.NOFILTER) {
            return new Response(true, "");
        } else {
            String response = checkmessage(text);
            return createChecked(response);
        }
    }


    public String checkmessage(String text) {
        try {
            String safeMessage = makeSafeForJson(text);

            String systemprompt = "You are a moderator. Check the following German text. " +
                    "**Rules: **" +
                    "1. If the text does NOT meet the conditions: Respond ONLY with: [false] " +
                    "2. If the text meets the conditions (including hidden ones such as Leetspeak, e.g., ‘3’ instead of ‘e’): Respond with: [true] - followed by the words that meet the conditions and a brief explanation of why you recognized a word (max. 1 sentence). " +
                    "3. Send [true] or [false] first, nothing else!!! The first thing you send must be one of these!" +
                    "4. This also applies if the word is hidden, i.e. letters have been swapped or reversed. " +

                    "Conditions that should be [true]:" +
                    sensitivity.getConditions() +

                    "Exceptions that should be [false]:" +
                    sensitivity.getExceptions();

            String saveSystempromt = makeSafeForJson(systemprompt);

            String jsonBody = "{\n" +
                    "  \"model\": \"" + aiModel + "\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"system\", \"content\": \"" + saveSystempromt + "\"},\n" +

                    makeContextForJson(sensitivity.getExample())  +

                    "    {\"role\": \"user\", \"content\": \"Analyze this text: " + safeMessage + "\"}\n" +
                    "  ],\n" +
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
            e.printStackTrace();
            return "[error] connection problem";
        }
    }

    private Response createChecked(String response) {
        String stripedResponse = response.strip();
        String lowerResponse = stripedResponse.toLowerCase();

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

    public Sensitivity getSensitivity() {
        return sensitivity;
    }
    public void setSensitivity(Sensitivity sensetivity) {
        this.sensitivity = sensetivity;
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
    }
    public double getTop_p() {
        return top_p;
    }
    public void setTop_p(double top_p) {
        this.top_p = top_p;
    }
    public int getMax_tokens() {
        return max_tokens;
    }
    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
