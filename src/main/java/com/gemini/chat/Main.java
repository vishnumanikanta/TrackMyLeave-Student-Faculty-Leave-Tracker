package com.gemini.chat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    static final String API_KEY = "AIzaSyA7A50sVnIX95uR2Z9b1cVmL4H_KNxyH0g"; // your Gemini API key

    public static String askGemini(String prompt) throws Exception {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

        // Build request payload
        String jsonInput = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"" + prompt.replace("\"", "\\\"") + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Send POST request
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        // Read response
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line.trim());
        }
        br.close();

        // Parse response
        JSONObject obj = new JSONObject(response.toString());
        JSONArray candidates = obj.getJSONArray("candidates");
        if (candidates.length() == 0) return "❌ No response from Gemini.";
        JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        return parts.getJSONObject(0).getString("text");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("🤖 Gemini 2.0 Flash ChatBot (Type 'exit' to quit)");

        while (true) {
            System.out.print("\nYou: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String response = askGemini(input);
            System.out.println("\nBot: " + response);
        }

        sc.close();
    }
}
