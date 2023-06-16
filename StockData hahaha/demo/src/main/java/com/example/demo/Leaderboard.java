package com.example.demo;

import java.awt.Desktop;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class Leaderboard {
    public static Map<String, TradingUser> users = new HashMap<>();
    public static List<TradingUser> userListday;
    public static List<TradingUser> userListcumulative;

    public Leaderboard() {
    }

    public static void updateData(UserAuthenticationSystem a) {
        // Get the users from the UserAuthenticationSystem
        users = a.users;
        // Create a list of users
        userListday = new ArrayList<>(users.values());
        userListcumulative= new ArrayList<>(users.values());
        // Sort the users based on plPoints in descending order
        Collections.sort(userListday, Comparator.comparingDouble(TradingUser::getPlPointsofday).reversed());
        
        // Sort the users based on cumulativePLPoints in descending order
        Collections.sort(userListcumulative, Comparator.comparingDouble(TradingUser::getcumulativePLPoints).reversed());
    }

    public static void showRanking(UserAuthenticationSystem a){
        updateData(a);
        System.out.println("\n=========================================================================================================================================\n");
        System.out.println("=========================================================================================================================================\n");
        System.out.println("                                                Ranking based on Daily PL Points :");
        System.out.println("=========================================================================================================================================\n");
        int rank = 1;
        for (TradingUser user : userListday) {
            String output = String.format("Rank %d :  %-20s - Daily PL points  : %-10s",
                                        rank, user.getUsername(), user.getPlPointsofday());
            System.out.println(output);
            rank++;
        }

        System.out.println("=========================================================================================================================================\n");
        System.out.println("=========================================================================================================================================\n");
        System.out.println("                                                Ranking based on cumulative PL Points:");
        System.out.println("=========================================================================================================================================\n");
        rank = 1;
        for (TradingUser user : userListcumulative) {
            String output = String.format("Rank %d : %-20s - Cumulative PL points  : %-10s",
                                        rank, user.getUsername(), user.getcumulativePLPoints());
            System.out.println(output);
            rank++;
        }

        System.out.println("=========================================================================================================================================\n");
        System.out.println("=========================================================================================================================================\n");

    }

    //update the buy and sellorders for html
    public static void updateRealTimeSystemQueue(UserAuthenticationSystem a) {        
        updateData(a);
        // Initialize Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("static/leaderboard/");
        templateEngine.setTemplateResolver(templateResolver);
        // Prepare the template context and set the variable
        Context context = new Context();
        context.setVariable("userListday", userListday);
        context.setVariable("userListcumulative", userListcumulative);
        // Process the template with the context
        String processedHtml = templateEngine.process("leaderboard.html", context);

        try {
            // Write the processed HTML to the initial template HTML file
            String templatePath = "demo\\src\\main\\resources\\static\\leaderboard\\leaderboardfinal.html";
            FileWriter writer = new FileWriter(templatePath);
            writer.write(processedHtml);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openRealTimeSystemQueueInBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:5500/demo/src/main/resources/static/leaderboard/leaderboardfinal.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void RenderHTMLwebsite(UserAuthenticationSystem a){
        //update periodically the leaderboard for 1 min
        Timer timer = new Timer();
        TimerTask currentTask2 = new TimerTask() {
            @Override
            public void run() {
                updateRealTimeSystemQueue(a);
            }
        };
        timer.schedule(currentTask2, 0, 1* 60 * 1000);
    }
}

