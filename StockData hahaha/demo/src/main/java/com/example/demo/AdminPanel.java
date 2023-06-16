package com.example.demo;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminPanel {
    
    public AdminPanel() {
        // Constructor
    }

    // Print all user info
    public static void PrintAllUsersInfo(UserAuthenticationSystem a) {
        Map<String, TradingUser> users = a.users;
        System.out.println("=========================================================================================================================================\n");
        System.out.println("=========================================================================================================================================\n");
        System.out.println("Printing users info...............");
        System.out.println("=========================================================================================================================================\n");
        if (users.isEmpty()) {
            System.out.println("[ADMIN PANEL] \nNo users found.");
            System.out.println("=========================================================================================================================================\n");
            return;
        }
        
        System.out.println("[ADMIN PANEL] \nUser Information:");
        int labelWidth = 25;
        for (TradingUser user : users.values()) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Username", user.username));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Email", user.getEmail()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Account Balance", user.getAccountBalance()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Daily PL Points", user.getPlPointsofday()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Cumulative PL Points", user.getcumulativePLPoints()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Starting Balance", user.getStartingBalance()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Cumulative Difference", user.getcumulativeDifference()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "End of Day Balance", user.getEndOfDayBalance()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Qualification Next Day", user.checkQualificationForNextDay()));
            System.out.println(String.format("%-" + labelWidth + "s: %s", "Difference for Day", user.getDifferenceForDay()));
            System.out.println("\nUser's owned stocks: \n"+user.getHeldStocks(1)+"\n");
            System.out.println("\nUser's buying order: \n"+user.getBuyList(1)+"\n");
            System.out.println("\nUser's listing stocks: \n"+user.getListedStocks(1)+"\n");
            System.out.println("\nUser's trade history: \n"+user.getTradeHistory(1)+"\n");
            System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        System.out.println("=========================================================================================================================================\n");
        System.out.println("User info finished printing...............");
        System.out.println("=========================================================================================================================================\n");
    }

    //Modify the qualification of a user?
    public static void modifyuserqualification(UserAuthenticationSystem a, String email, boolean b) {
        System.out.println("=========================================================================================================================================\n");
        try {
            if (b) {
                System.out.println("Enabling qualification of user " + a.getUser(email).username);
            } else {
                System.out.println("Banning user " + a.getUser(email).username);
            }
            a.getUser(email).setQualifiedForNextDay(b);
            System.out.println("======================================================   Operation Done   ===============================================================\n");
            System.out.println("=========================================================================================================================================\n");
        } catch (NullPointerException e) {
            System.out.println("Error: User with the provided email does not exist.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        System.out.println("=========================================================================================================================================\n");
    }

    // Modify initial trading period 
    public static void setInitialTradingPeriod(boolean a){
        System.out.println("=========================================================================================================================================\n");
        try {
            if (a) {
                System.out.println("Enabling initial trading period.............");
            } else {
                System.out.println("Disabling initial trading period.............");
            }
            StockTradingSystem.setInitialTradingPeriod(a);
            System.out.println("======================================================   Operation Done   ===============================================================\n");
            System.out.println("=========================================================================================================================================\n");
        } catch (Exception e) {
            System.out.println("System error. Operation cannot be done.");
        }
        System.out.println("=========================================================================================================================================\n");

    }

    // replenish lot pool to initial 500-lot per stock
    public static void ReplenishLotPool(){
        System.out.println("=========================================================================================================================================\n");
        try {
            System.out.println("Replenishing lot pool.............\n");
            StockTradingSystem.replenishLotPool();
            System.out.println("======================================================   Operation Done   ===============================================================\n");
            System.out.println("=========================================================================================================================================\n");
        } catch (Exception e) {
            System.out.println("System error. Operation cannot be done.");
        }
        System.out.println("=========================================================================================================================================\n");
    }

    // illustrate the lot pool and system queue
    public static void checkLotpoolNSystemQueue(){
        System.out.println("=========================================================================================================================================\n");
        System.out.println("Showing you current Lot-Pool & System Queue.....\n");
        StockTradingSystem.showLotPoolShares();
        StockTradingSystem.displayBuyOrders();
        StockTradingSystem.displaySellOrders();
        System.out.println("=========================================================================================================================================\n");
    }

    // admin start matching 
    public static void startMatching(){
        System.out.println("=========================================================================================================================================\n");
        StockTradingSystem.startMatching();
        System.out.println("Operation done");        
        System.out.println("=========================================================================================================================================\n");

    }

    // admin stop matching
    public static void stopMatching(){
        System.out.println("=========================================================================================================================================\n");
        StockTradingSystem.stopMatching();
        System.out.println("Operation done");
        System.out.println("=========================================================================================================================================\n");

    }

    //admin to start the day of trading
    public static void startofperiod(){
        System.out.println("=========================================================================================================================================\n");
        StockTradingSystem.StartOfPeriod();
        System.out.println("Operation done");
        System.out.println("=========================================================================================================================================\n");
        
    }

    //admin to end the day of trading
    public static void EndOfPeriod(){
        System.out.println("=========================================================================================================================================\n");
        StockTradingSystem.EndOfPeriod();
        System.out.println("Operation done");
        System.out.println("=========================================================================================================================================\n");
    }

    //reset password for user
    public static void AdminResetUserPassword(UserAuthenticationSystem a, String email){
        System.out.println("=========================================================================================================================================\n");
        System.out.println("=============================================   Admin privileged password change   ======================================================\n");
        Scanner xx=new Scanner(System.in);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            System.out.println("Your new password: ");
            String gg = xx.nextLine();                
            a.getUser(email).password= encoder.encode(gg);
            if (a.getUser(email).notificationgeneral) {
                Notification.showNotification("Sensitive Information Alert", "Your password has been changed successfully");
            }if(a.getUser(email).notificationemail){
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Recent changes to sensitive information on EZTrade",
                                "Changes on your password on EZTrade",
                                "Dear " + a.getUser(email).getUsername() +
                                ", we found out that you had made recent changes on your password!" +
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
            }
        System.out.println("\nYour password has been changed successfully!\n");
        System.out.println("=========================================================================================================================================\n");
    }
}
