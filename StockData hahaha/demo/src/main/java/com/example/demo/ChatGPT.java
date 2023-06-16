package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {

    public static void askGPT(String yourQuestionhere) {
        System.out.println("=========================================================================================================================================");
        System.out.println("EZTrade bot :");
        String answer=""+chatGPT(yourQuestionhere);
        ExecutorService executor = Executors.newSingleThreadExecutor();
                    // Run the gpt function in a separate thread
        executor.execute(() -> {
        int delay=40;
        for (int i = 0; i < answer.length(); i++) {
            System.out.print(answer.charAt(i));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
        System.out.println("\n=========================================================================================================================================");
        System.out.println("Your Question to Virtual Chat Agent: ");
        System.out.print("=========================================================================================================================================\n>");
        });        
        executor.shutdown();
        // Prints out a response to the question.
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject messageObject = firstChoice.getJSONObject("message");
            String content = messageObject.getString("content");

            return "Hi, I'm EZTrade virtual bot to assist you for the best of your experience :D\n\n"+content;

        } catch (IOException e) {
            return "Virtual Chat Agent has issue now ,please contact admin for further diagnosis.";
            
        }
    }

}