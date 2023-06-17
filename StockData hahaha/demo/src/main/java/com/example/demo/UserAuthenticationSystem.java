package com.example.demo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserAuthenticationSystem implements Serializable {
    public Map<String, TradingUser> users = new HashMap<>();
    public Map<String, TradingUser> userSessions = new HashMap<>();

    // ****** Dont create new variable, direct access
    // ****** if log in , boolean true all the console, notification and email
    // ****** if log out, boolean flase all the console, notification and email


    // -------> to do : remember to reset the difference for day, reset PL for day at the system close !!!!!!!!!! highly important
    //1. Method for registration
    // Register a user in the system
    public void registerUser(String username, String email, String password) {
        // Check if email already exists
        if (userExists(email)) {
            System.out.println("\n[SYSTEM] : User already exists. Please try with a different email!");
            Notification.showNotification("EZ Trade", "User already exists. Please try with a different email!");
            return; // Exit the method if email exists
        }

        // Check if username already exists
        for (TradingUser user : users.values()) {
            if (user.getUsername().equals(username)) {
                System.out.println("\n[SYSTEM] : Username already exists. Please try with a different username!");
                Notification.showNotification("EZ Trade", "Username already exists. Please try with a different username!");
                return; // Exit the method if username exists
            }
        }

        // Encrypt the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        // Create a new user
        TradingUser user = new TradingUser(username, hashedPassword, email, 50000);
        users.put(email, user);

        System.out.println("\n[SYSTEM] : Registered successfully!\n");
        Notification.showNotification("EZ Trade", "Registered successfully!");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            EmailFunction.sendMail(email, "Registration successful on EZTrade",
                    "Account creation successful",
                    "Dear " + username +
                            "<p>, your account has been successfully created on EZTrade.</p>" +
                            "<p> We appreciate and welcome you to experience the FSKTM first ever stock trading platform! </p>" +
                            "<p>Feel free to ask for support and we will see you on the trading battle! </p>" +
                            "</b></p><p>Do check your account for more details!</p>" +
                            "<p>---" + ClockFunction.getStringCurrentTime() + "</p>");
        });
        // Shutdown the executor
        executor.shutdown();
    }

    // Check if a user with the given email exists in the system
    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    // Get a user from the system 
    public TradingUser getUser(String email) {
        return users.get(email);
    }

    // Authenticate a user in the system
    public boolean authenticateUser(String email, String password) {
        if (userExists(email)) {
            TradingUser user = getUser(email);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(password, user.password);
        }
        return false;
    }

    //2. methods for login purpose
    // Login a user in the system
    public boolean loginUser(String email, String password) {
        if (userSessions.containsKey(email)) {
            System.out.println("\n[SYSTEM] : Already logged in!\n");
            Notification.showNotification("Alert", "Already logged in! ");
            return false;
        }
        if (authenticateUser(email, password)) {
            TradingUser user = getUser(email);
            
            if (user != null) {
                if(user.qualifiedForNextDay==false){
                    System.out.println("\n[SYSTEM] : You are disqualified ! If you believe this is bug, please contact the admin\n");
                    Notification.showNotification("Info","You are disqualified ! If you believe this is bug, please contact the admin.");
                    return false;
                }
                if (!user.isFirstTimeLogin()) {
                    user.setStartingBalance();
                    user.setFirstTimeLogin(true);
                }
                userSessions.put(email, user);
                System.out.println("\n[SYSTEM] : Logged in successfully!\n");
                Notification.showNotification("Info","Logged in successfully! ");
                user.setConsoleshow(true);
                if(user.realnotificationgeneral){
                    user.setNotificationgeneral(true);
                }
                else{
                    user.setNotificationgeneral(false);
                } 
                return true;
            } else {
                System.out.println("\n[SYSTEM] : User not found, please contact admin\n");
                Notification.showNotification("Alert","\nUser not found ");
                return false;
            }
        } else {
            System.out.println("\n[SYSTEM] : Wrong Password or Email ! Please try again!\n");
            Notification.showNotification("Alert","\nWrong Password or Email ! Please try again!");
            return false;
        }
    }

    // Reset the firstTimeLogin status for all users at the end of day
    public void resetFirstTimeLogin() {
        for (TradingUser user : users.values()) {
            user.setFirstTimeLogin(false);
        }
    }

    // 3. methods for logout purpose
    // Logout a user from the system
    public void logoutUser(String email) {
        TradingUser user = userSessions.get(email);
        if (user != null) {
            userSessions.remove(email);
            System.out.println("\n[SYSTEM] : Logged out successfully\n");
            Notification.showNotification("Alert","\nLogged out successfully ");
            user.setConsoleshow(false);
            user.setNotificationgeneral(false);
        } else {
            System.out.println("\n[SYSTEM] : User not found");
            Notification.showNotification("Alert","\nUser not found");
        }
    }

    // Save all users to a binary file
    public void saveUsers() {
        try {
            FileOutputStream fileOut = new FileOutputStream("demo\\src\\main\\resources\\static\\userdata1.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
            System.out.println("\n[SYSTEM] : User data saved successfully\n");
        } catch (IOException i) {
            System.out.println("=========================================================================================================================================\n");
            System.out.println("=================================================        Error Log       ================================================================\n");
            i.printStackTrace();
            System.out.println("=========================================================================================================================================\n");
        }
    }

    // Load all users from a binary file
    @SuppressWarnings("unchecked")
    public void loadUsers() {
        try {
            FileInputStream fileIn = new FileInputStream("demo\\src\\main\\resources\\static\\userdata1.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof Map<?, ?>) {
                users = (Map<String, TradingUser>) obj;
                System.out.println("\n[SYSTEM]: User data retrieved successfully\n");
            } else {
                System.out.println("\n[SYSTEM] : Invalid data format \n");
            }
            in.close();
            fileIn.close();
        }
        catch (Exception c) {
            System.out.println("=========================================================================================================================================\n");
            System.out.println("=================================================        Error Log       ================================================================\n");
            c.printStackTrace();
            System.out.println("=========================================================================================================================================\n");
        }
    }

    //3. method to load the user database
    // Initiate the system by loading user data
    public void initiateSystem() {
        loadUsers();
    }

    //4. method to save the user database
    // Close the system by saving user data
    //will check for the qualification for next day instead of manual checking
    public void closeSystem() {
        resetFirstTimeLogin();
        for (TradingUser user : users.values()) {
            user.setQualificationForNextDay();
            user.resetPlPointsforDay();
            user.resetDifferenceForDay();
        }
        saveUsers();
        System.out.println("[SYSTEM] : End of trading day! See you tomorrow!");
    }
}
