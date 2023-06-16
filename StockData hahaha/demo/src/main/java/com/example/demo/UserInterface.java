package com.example.demo;

import java.time.LocalTime;
import java.util.Scanner;

public class UserInterface {

        static Scanner xx = new Scanner(System.in);

        public static void main(String[] args) {
                // initiate the userauthentication system
                UserAuthenticationSystem a = new UserAuthenticationSystem();
                // a -> object of the user repository system

                // start of day shall be configured by the admin
                // but for demo purpose lets do it manually

                //admin task 1 --->
               // StockTradingSystem.StartOfDay();

                // initiate the previous user data ( can be null or yes)
                try {
                        a.initiateSystem();
                } catch (Exception i) {
                        System.out.println("\n[SYSTEM]: No Previous User data! ");
                }
                boolean status1 = true;
                // request input from users                 
                StockTradingSystem.RenderHTMLwebsite();
                Leaderboard.RenderHTMLwebsite(a);

                try {
                while (status1) {
                        System.out.println(
                                "=========================================================================================================================================");
                        System.out.println(
                                "\n\n\n      ███████╗███████╗  ████████╗██████╗░░█████╗░██████╗░███████╗\n      ██╔════╝╚════██║  ╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔════╝\n      █████╗░░░░███╔═╝  ░░░██║░░░██████╔╝███████║██║░░██║█████╗░░\n      ██╔══╝░░██╔══╝░░  ░░░██║░░░██╔══██╗██╔══██║██║░░██║██╔══╝░░\n      ███████╗███████╗  ░░░██║░░░██║░░██║██║░░██║██████╔╝███████╗\n      ╚══════╝╚══════╝  ░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═════╝░╚══════╝\n\n\n                     ___     _           \n                    | __|_ _| |_ ___ _ _ \n      >             | _|| ' \\  _/ -_) '_|          <\n                    |___|_||_\\__\\___|_|  ");
                        xx.nextLine();
                        System.out.print(
                                "========================================================================================================================================="
                                        + "\nWelcome to EZTrade! " +
                                        "\n-----------------------------------------------------------------------------------------------------------------------------------------"
                                        + "\n 1- REGISTER" + "\n 2- LOGIN" + "\n 3- ADMIN PANEL"
                                        + "\n-----------------------------------------------------------------------------------------------------------------------------------------"
                                        + "\n=========================================================================================================================================\n>");
                        String input = xx.nextLine();

                        if (input.equals("1") || input.equals("2") || input.equals("3")) {
                        int option = Integer.parseInt(input);
                        // Valid input, proceed with the corresponding action
                        switch (option) {
                                case 1:
                                // Handle registration
                                System.out.println(
                                        "  _____            _     _             _   _             \n |  __ \\          (_)   | |           | | (_)            \n | |__) |___  __ _ _ ___| |_ _ __ __ _| |_ _  ___  _ __  \n |  _  // _ \\/ _` | / __| __| '__/ _` | __| |/ _ \\| '_ \\ \n | | \\ \\  __/ (_| | \\__ \\ |_| | | (_| | |_| | (_) | | | |\n |_|  \\_\\___|\\__, |_|___/\\__|_|  \\__,_|\\__|_|\\___/|_| |_|\n              __/ |                                      \n             |___/                                       ");
                                // String username, String email, String password
                                System.out.print(
                                        "==============================================================================");
                                System.out.println("\nKey in your username [can be changed later] :\n");
                                String username = xx.nextLine();
                                System.out.print(
                                        "==============================================================================");
                                System.out.println("\nKey in your login email [usable email] :\n");
                                String email = xx.nextLine();
                                System.out.print(
                                        "==============================================================================");
                                System.out.println("\nKey in your password :\n");
                                String password = xx.nextLine();
                                System.out.print(
                                        "==============================================================================");
                                a.registerUser(username, email, password);
                                System.out.print(
                                        "==============================================================================");
                                // handled by the system no need add on ( popped back into the main page)
                                break;
                                case 2:
                                // Handle login
                                System.out.println(
                                        "  _                 _       \n | |               (_)      \n | |     ___   __ _ _ _ __  \n | |    / _ \\ / _` | | '_ \\ \n | |___| (_) | (_| | | | | |\n |______\\___/ \\__, |_|_| |_|\n               __/ |        \n              |___/         ");

                                System.out.print(
                                        "==============================================================================");
                                System.out.println("\nLog in email :\n");
                                String email2 = xx.nextLine();
                                System.out.print(
                                        "==============================================================================");
                                System.out.println("\nPassword :\n");
                                String password2 = xx.nextLine();
                                System.out.print(
                                        "==============================================================================");
                                if (a.loginUser(email2, password2)) {
                                        a.getUser(email2).RenderHTMLuserprofileWebsite(true);
                                        Lobby(a, email2);
                                } else {
                                        break;
                                }
                                // out from lobby
                                a.logoutUser(email2);
                                a.getUser(email2).RenderHTMLuserprofileWebsite(false);
                                break;

                                // admin case (login)                                
                                // exit system only accessible by admin
                                case 3:
                                String wow;
                                while (true) {
                                        System.out.println("=========================================================================================================================================\n");
                                        System.out.println("                       ___    ___      _           _        ______                _   ___ \n" +
                                                        "                      |  _|  / _ \\    | |         (_)       | ___ \\              | | |_  |\n" +
                                                        "                      | |   / /_\\ \\ __| |_ __ ___  _ _ __   | |_/ /_ _ _ __   ___| |   | |\n" +
                                                        "                      | |   |  _  |/ _` | '_ ` _ \\| | '_ \\  |  __/ _` | '_ \\ / _ \\ |   | |\n" +
                                                        "                      | |   | | | | (_| | | | | | | | | | | | | | (_| | | | |  __/ |   | |\n" +
                                                        "                      | |_  \\_| |_/\\__,_|_| |_| |_|_|_| |_| \\_|  \\__,_|_| |_|\\___|_|  _| |\n" +
                                                        "                      |___|                                                          |___|\n");
                                        System.out.println("=========================================================================================================================================\n");
                                        System.out.println("=========================================================================================================================================\n");              
                                        System.out.print("\nEnter admin username : "); // admin username is admin
                                        wow = xx.nextLine().toUpperCase();
                                        if (wow.equals("ADMIN")) {
                                                System.out.print("\nEnter admin password : "); // admin password is admin
                                                wow = xx.nextLine();
                                                System.out.println();
                                                if (!wow.equals("admin")) {
                                                        System.out.println("\n~ Incorrect admin password ~");
                                                        break;
                                                }
                                                else{
                                                        String question = "";
                                                        System.out.println("=========================================================================================================================================\n");
                                                        System.out.println("===============================================     Welcome to Admin Panel  =============================================================\n");
                                                        System.out.println("=========================================================================================================================================\n");
                                                        while (!question.toLowerCase().contains("bye")) {
                                                                System.out.println("=========================================================================================================================================\n");
                                                                System.out.println("-1 Print All Users Info\n-2 User Qualification Matters\n-3 Set Initial Trading Period"+
                                                                "\n-4 Replenish 500-Lot Pool\n-5 Peek 500-Lot Pool & System Queue\n-6 Start System Automated Matching"+
                                                                "\n-7 Stop Matching\n-8 Start The Trading Day (alongside with start matching & update latest stock data)"+
                                                                "\n-9 End the Trading Period (stop the automated Matching & clear system queue)\n-10 Admin Privileged Reset Password"
                                                                +"\n-11 Save User Data and End of Day (Cannot be undo, proceed to next day)\n");
                                                                System.out.println("=========================================================================================================================================\n");
                                                                String inpussss = xx.nextLine();
                                                                switch (inpussss) {
                                                                        case "2": {
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                System.out.print("Enter user email to modify: \n>");
                                                                                String mock=xx.nextLine();
                                                                                System.out.print("Modify their qualification [True/False]: \n>");
                                                                                String quali=xx.nextLine();
                                                                                boolean qualiornot;
                                                                                if(quali.equalsIgnoreCase("true")){
                                                                                        qualiornot=true;
                                                                                }
                                                                                else{
                                                                                        qualiornot=false;
                                                                                }
                                                                                AdminPanel.modifyuserqualification(a, mock, qualiornot);
                                                                                break;
                                                                        }       
                                                                        case "1": {
                                                                                AdminPanel.PrintAllUsersInfo(a);
                                                                                break;
                                                                        }
                                                                        case "3": {
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                System.out.print("Set initial Trading Period [True/False]: \n>");
                                                                                String mock=xx.nextLine();
                                                                                boolean initialornot;
                                                                                if(mock.equalsIgnoreCase("true")){
                                                                                        initialornot=true;                                                                                
                                                                                        AdminPanel.setInitialTradingPeriod(initialornot);

                                                                                }
                                                                                else if(mock.equalsIgnoreCase("false")){
                                                                                        initialornot=false;
                                                                                        AdminPanel.setInitialTradingPeriod(initialornot);
                                                                                }
                                                                                break;
                                                                        }
                                                                        case "4":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                System.out.print("ReplenishLotPool ? [True/False]: \n>");
                                                                                String mock=xx.nextLine();
                                                                                if(mock.equalsIgnoreCase("true")){                                                                                
                                                                                        AdminPanel.ReplenishLotPool();
                                                                                }
                                                                                break;
                                                                        }
                                                                        case "5":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                AdminPanel.checkLotpoolNSystemQueue();
                                                                                break;
                                                                        }
                                                                        case "6":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                AdminPanel.startMatching();
                                                                                break;
                                                                        }
                                                                        case "7":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                AdminPanel.stopMatching();
                                                                                break;
                                                                        }
                                                                        case "8":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                AdminPanel.startofperiod();
                                                                                break;
                                                                        }
                                                                        case"9":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                AdminPanel.EndOfPeriod();
                                                                                break;
                                                                        }
                                                                        case "10":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                try{
                                                                                        System.out.print("Enter user email to modify password: \n>");
                                                                                        String mock=xx.nextLine();
                                                                                        AdminPanel.AdminResetUserPassword(a, mock);
                                                                                }
                                                                                catch(Exception e){
                                                                                        System.out.println("No such user!");
                                                                                        System.out.println("=========================================================================================================================================\n");
                                                                                }
                                                                                break;
                                                                        }
                                                                        case "11":{
                                                                                System.out.println("=========================================================================================================================================\n");
                                                                                if(a.users==null){
                                                                                        break;
                                                                                }
                                                                                a.closeSystem();
                                                                                break;
                                                                        }
                                                                        default:break;
                                                                }
                                                        System.out.println("\n~Type <bye> to get out of here~");
                                                        question = xx.nextLine();
                                                        }
                                                        
                                                }
                                        System.out.println(
                                                "=========================================================================================================================================");
                                        System.out.println(
                                                "=========================================================================================================================================");
                                        } else {
                                        System.out.println("=========================================================================================================================================");
                                        System.out.println("\n~ Incorrect admin username ~");
                                        System.out.println(
                                                "=========================================================================================================================================");
                                        break;
                                }
                                System.out.println(
                                        "=========================================================================================================================================");
                                break;
                        }
                                default:
                                        break;
                                // back to main interface if none of the input are viable
                        }
                        } else {
                        // back to main interface
                        }
                }
                } catch (Exception e) {
                        System.out.println("=========================================================================================================================================\n");
                        System.out.println("====================================================      Error Logs      ===============================================================\n");
                        e.printStackTrace();
                        System.out.println("=========================================================================================================================================\n");
                        System.out.println("\nSystem is experiencing lag....don't worry user data is saved! ");
                        System.out.println("=========================================================================================================================================\n");
                        StockTradingSystem.EndOfPeriod();
                        a.closeSystem();
                        System.out.println("=========================================================================================================================================\n");
                        System.exit(0);
                }
        }

        // receive the email from the login page
        // Main lobby
        public static void Lobby(UserAuthenticationSystem a, String email) {
                while (true) {
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "========================================================  Lobby  ========================================================================");
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println("\n1-   EZTrade Chat Bot\n2-   EZTrade Trading Real Time Stock Data & System Queue" +
                        "\n3-   Your Dashboard & Profile Portfolio\n4-   Leaderboard\n5-   Notification settings\n6-   Reporting\n7-   Trade\n8-   Cancel Transaction\n9-   Sign Out"
                        +
                        "\n10-  Change User Info\n");
                System.out.println(
                        "=========================================================================================================================================");
                String input = xx.nextLine();
                try {
                        // handle exception
                        switch (input) {
                        case "1": { //done
                                EZTradeChatBot();
                                break;
                                }   
                        case "2": { //done
                                realTimeStockData();
                                break;
                                }
                        case "3": {  //done
                                YourDashboardandPortfolio(a, email);
                                break;
                        }
                        case "4": {  //done
                                Leaderboard(a,email);
                                break;
                        }
                        case "5": {  //done
                                NotificationSetting(a, email);
                                break;
                        }
                        case "6":{ //done
                                Reporting(a,email);
                                break;
                        }
                        case "7": {  //done
                                LocalTime currentTime = LocalTime.now();
                                LocalTime marketStartTime1 = LocalTime.of(9, 0);  // Regular market start time (9:00 AM)
                                LocalTime marketEndTime1 = LocalTime.of(12, 30); // Regular market end time (12:30 PM)
                                LocalTime marketStartTime2 = LocalTime.of(14, 30); // Regular market start time (2:30 PM)
                                LocalTime marketEndTime2 = LocalTime.of(17, 0); // Regular market end time (5:00 PM)
                                System.out.println("=========================================================================================================================================\n");

                                if ((currentTime.isAfter(marketStartTime1) && currentTime.isBefore(marketEndTime1)) ||
                                        (currentTime.isAfter(marketStartTime2) && currentTime.isBefore(marketEndTime2))) {
                                        Trade(a, email);
                                } else {
                                        System.out.println("\nIt is currently not within trading hours. Please come back during regular market hours.");
                                        System.out.println("=========================================================================================================================================\n");
                                        break;
                                }
                                break;
                        }
                        case "8": {  //done
                                CancelTransaction(a, email);
                                break;
                        }
                        case "9": {  //done
                                // User has chosen to sign out, do nothing and exit the loop
                                break;
                        }
                        case "10": {  //done
                                ChangeUserInfo(a, email);
                                break;
                        }
                        //secret bug to see bug 
                        case "test": {
                                System.out.println("Test name :"+a.getUser(email).getUsername());
                                System.out.println("Account balance " + a.getUser(email).getAccountBalance());
                                System.out.println("starting balance " + a.getUser(email).getStartingBalance());
                                System.out.println("pl day " + a.getUser(email).getPlPointsofday());
                                System.out.println("pl cumulative " + a.getUser(email).getcumulativePLPoints());
                                System.out.println("difference for day " + a.getUser(email).getDifferenceForDay());
                                System.out.println("difference cumulative " + a.getUser(email).getcumulativeDifference());
                                System.out.println("qualified for next day " + a.getUser(email).checkQualificationForNextDay());
                                break;
                        }
                        default: {   //invalid input
                                while (Double.parseDouble(input) < 1 || Double.parseDouble(input) > 10) {
                                System.out.println("\nInvalid Options, key in range [1-10] only :\n");
                                input = xx.nextLine();
                                }
                        }
                        }
                        if (input.equals("9")) {
                        System.out
                                .print("\n==============================================================================");
                        System.out.print("\nAre you about to sign out ? [Y/N]\n\n");
                        System.out
                                .print("==============================================================================\n");
                        if (xx.nextLine().equalsIgnoreCase("Y")) {
                                break;
                        }
                        }
                } catch (Exception e) {
                        // Handle the exception
                }
                }
        }

        // -option 3- show the user's dashboard
        public static void YourDashboardandPortfolio(UserAuthenticationSystem a, String email) {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "                      ______          _     _                         _                ______          _    __      _ _       \n"
                                + "                      |  _  \\        | |   | |                       | |     ___       | ___ \\        | |  / _|    | (_)      \n"
                                + "                      | | | |__ _ ___| |__ | |__   ___   __ _ _ __ __| |    ( _ )      | |_/ /__  _ __| |_| |_ ___ | |_  ___  \n"
                                + "                      | | | / _` / __| '_ \\| '_ \\ / _ \\ / _` | '__/ _` |    / _ \\/\\    |  __/ _ \\| '__| __|  _/ _ \\| | |/ _ \\ \n"
                                + "                      | |/ / (_| \\__ \\ | | | |_) | (_) | (_| | | | (_| |   | (_>  <    | | | (_) | |  | |_| || (_) | | | (_) |\n"
                                + "                      |___/ \\__,_|___/_| |_|_.__/ \\___/ \\__,_|_|  \\__,_|    \\___/\\/    \\_|  \\___/|_|   \\__|_| \\___/|_|_|\\___/\n");
                System.out.println(
                        "=========================================================================================================================================");
                while (!question.toLowerCase().contains("bye")) {
                System.out.println(
                        "Choose Preferences:\n1- Native Dashboard\n2- Native Profile Portfolio\n3- Interactive Dashboard (New updates!)");
                System.out.print("\nInput range of [1-3]\n>");
                String userInput = xx.nextLine();
                System.out.println();
                if (userInput.equals("1")) {
                        TradingDashboard.DashBoard(a.getUser(email));
                        System.out.println(
                                "=========================================================================================================================================");
                } else if (userInput.equals("2")) {
                        System.out.println(
                                "=========================================================================================================================================");
                        System.out.println(
                                "====================================================    Portfolio Section    ============================================================\n");
                        System.out.println(
                                "Portfolio preferences :\n1- Ascending Stock Name\n2- Descending Stock Name\n3- Ascending Stock Shares"
                                        +
                                        "\n4- Descending Stock Shares\n5- Ascending Stock Prices\n6- Descending Stock Prices\n7- Ascending Placement Time\n8- Descending Placement Time");
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.print(" > Your Owned Stocks < \n > Preferences: ");
                        String prefer = xx.nextLine();
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.println(a.getUser(email).getHeldStocks(Integer.parseInt(prefer)));
                        if (a.getUser(email).heldStockss.isEmpty()) {
                        System.out.println("\nIt's empty :<");
                        }
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.print(" > Your Listing Stocks < \n > Preferences: ");
                        prefer = xx.nextLine();
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.println(a.getUser(email).getListedStocks(Integer.parseInt(prefer)));
                        if (a.getUser(email).listedStockss.isEmpty()) {
                        System.out.println("\nIt's empty :<");
                        }
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.print(" > Your On-Going Orders < \n > Preferences: ");
                        prefer = xx.nextLine();
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.println(a.getUser(email).getBuyList(Integer.parseInt(prefer)));
                        if (a.getUser(email).buyLists.isEmpty()) {
                        System.out.println("\nIt's empty :<");
                        }
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.println(
                                "===============================================================   END   =================================================================\n");
                } else if(userInput.equals("3")) {
                        System.out.println("\nShowing you.....user info....\nYou can remain the windows opened or close it\n");
                        Sleep.sleepInSeconds(2);
                        a.getUser(email).openRealTimeUserProfile();
                }
                else{
                        
                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
                System.out.println(
                        "=========================================================================================================================================\n");

        }
        //- option 6- show reporting
        public static void Reporting(UserAuthenticationSystem a, String email){
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.print("                      ______                      _   _             \n                      | ___ \\                    | | (_)            \n                      | |_/ /___ _ __   ___  _ __| |_ _ _ __   __ _ \n                      |    // _ \\ '_ \\ / _ \\| '__| __| | '_ \\ / _` |\n                      | |\\ \\  __/ |_) | (_) | |  | |_| | | | | (_| |\n                      \\_| \\_\\___| .__/ \\___/|_|   \\__|_|_| |_|\\__, |\n                                | |                            __/ |\n                                |_|                           |___/ \n");
                System.out.println(
                        "=========================================================================================================================================");
                while (!question.toLowerCase().contains("bye")) {
                System.out.println(
                        "Choose Preferences to print report:\n1-PDF \n2-CSV\n3-TXT\n");
                System.out.print("\nInput range of [1-3]\n>");
                String userInput = xx.nextLine();
                System.out.println();
                if (userInput.equals("1")) {
                        System.out.println(
                        "=========================================================================================================================================\n");
                        System.out.println("Printing.....report as PDF......\n");
                        Reporting.downloadAsPdf(a.getUser(email));
                        System.out.println("\nOperation done.......");
                        System.out.println(
                        "=========================================================================================================================================\n");
                } else if (userInput.equals("2")) {
                        System.out.println(
                        "=========================================================================================================================================\n");
                        System.out.println("Printing.....report as CSV......\n");
                        Reporting.downloadAsCSV(a.getUser(email));
                        System.out.println("\nOperation done.......");
                        System.out.println(
                        "=========================================================================================================================================\n");
                } else if (userInput.equals("3")) {
                        System.out.println(
                        "=========================================================================================================================================\n");
                        System.out.println("Printing.....report as TXT......\n");
                        Reporting.downloadAsTXT(a.getUser(email));
                        System.out.println("\nOperation done.......");
                        System.out.println(
                        "=========================================================================================================================================\n");
                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
                System.out.println(
                        "=========================================================================================================================================\n");        
        }

        // -option 2- show real time stock data & system queue
        // Interface to show real time stock data
        public static void realTimeStockData() {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        " _______  _______  _______  _______  ___   _    ______   ___   _______  _______  ___      _______  __   __ \n"
                                +
                                "|       ||       ||       ||       ||   | | |  |      | |   | |       ||       ||   |    |   _   ||  | |  |\n"
                                +
                                "|  _____||_     _||   _   ||       ||   |_| |  |  _    ||   | |  _____||    _  ||   |    |  |_|  ||  |_|  |\n"
                                +
                                "| |_____   |   |  |  | |  ||       ||      _|  | | |   ||   | | |_____ |   |_| ||   |    |       ||       |\n"
                                +
                                "|_____  |  |   |  |  |_|  ||      _||     |_   | |_|   ||   | |_____  ||    ___||   |___ |       ||_     _|\n"
                                +
                                " _____| |  |   |  |       ||     |_ |    _  |  |       ||   |  _____| ||   |    |       ||   _   |  |   |  \n"
                                +
                                "|_______|  |___|  |_______||_______||___| |_|  |______| |___| |_______||___|    |_______||__| |__|  |___|  \n");
                System.out.println(
                        "=========================================================================================================================================");
                while (!question.toLowerCase().contains("bye")) {
                System.out.println(
                        "Choose Preferences:\n1-Native terminal Stock Search\n2-Native terminal Stock Display\n3-Native terminal System Queue & Lot-Pool\n4-Interactive board(New updates!)\n5-Real Time Historical Stock Price (Copyright MalaysiaStock.biz)\n");
                System.out.print("\nInput range of [1-5]\n>");
                String userInput = xx.nextLine();
                System.out.println();
                if (userInput.equals("1")) {
                        StockSearching.StockSearch();
                } else if (userInput.equals("2")) {
                        System.out.println(
                                "Choose Stock Display Preferences:\n1- Ascending Name\n2- DescendingName\n3- Ascending Price\n4- Descending Price\n5- Ascending Symbol\n6- Descending Symbol\n>");
                        String input = xx.nextLine();
                        while (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 6) {
                        System.out.println("\nInvalid Options, key in range [1-6] only :\n");
                        input = xx.nextLine();
                        }
                        StockDisplays.DisplayPreference(Integer.parseInt(input));
                } else if (userInput.equals("3")) {
                        System.out.println("Showing you current Lot-Pool & System Queue.....\n");
                        StockTradingSystem.showLotPoolShares();
                        StockTradingSystem.displayBuyOrders();
                        StockTradingSystem.displaySellOrders();
                } else if (userInput.equals("4")){
                        System.out.println("\nShowing you.....stock info....\nYou can remain the windows opened or close it\n");
                        Sleep.sleepInSeconds(2);
                        StockTradingSystem.openRealTime500lotpool();
                }
                else if(userInput.equals("5")){
                        System.out.println("\nPlease input the stock code without .KL to peek at the historical chart.......\n");
                        String code=xx.nextLine();
                        System.out.println("\nShowing you.....historical stock info....\nYou can remain the windows opened or close it\n");
                        HistoricalPerformance.openStockCodeURL(Integer.parseInt(code));
                        System.out.println(
                        "=========================================================================================================================================\n");
                }
                else{

                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
                System.out.println(
                        "=========================================================================================================================================\n");
        }

        // -option 1-Chat gpt bot
        public static void EZTradeChatBot() {
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "  ______ ______      _____ _____ _______ \n |  ____|___  /     / ____|  __ \\__   __|\n | |__     / /_____| |  __| |__) | | |   \n |  __|   / /______| | |_ |  ___/  | |   \n | |____ / /__     | |__| | |      | |   \n |______/_____|     \\_____|_|      |_|   ");
                System.out.println("\n\nWelcome to EZTradeChatBot\nAsk anything beyond your imagination!\n");
                System.out
                        .println("Warning that this chatbot may generate inaccurate response (it is trained before year 2021)");
                System.out.println("We are not responsible for any loss\n");
                System.out.println("\nTo end your conversation, simply write <bye> to the bot");
                System.out.println("Start your conversation here :\n");
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println("Your Question to Virtual Chat Agent: ");
                System.out.println(
                        "=========================================================================================================================================");
                while (!question.toLowerCase().contains("bye")) {
                System.out.print(">");
                question = xx.nextLine();
                if (question.toLowerCase().contains("bye")) {
                        break;
                }
                ChatGPT.askGPT(question);
                }
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println("I'm happy to served you! ~See you again~");
                System.out.println(
                        "=========================================================================================================================================\n");
        }

        // option 4 -Leaderboard to show rankin
        public static void Leaderboard(UserAuthenticationSystem a, String email){
                String question = "";
                System.out.println("=========================================================================================================================================\n");
                System.out.print("   ___   _                    _           _                         _   ___ \n"
                + "  |  _| | |                  | |         | |                       | | |_  |\n"
                + "  | |   | |     ___  __ _  __| | ___ _ __| |__   ___   __ _ _ __ __| |   | |\n"
                + "  | |   | |    / _ \\/ _` |/ _` |/ _ \\ '__| '_ \\ / _ \\ / _` | '__/ _` |   | |\n"
                + "  | |   | |___|  __/ (_| | (_| |  __/ |  | |_) | (_) | (_| | | | (_| |   | |\n"
                + "  |_|_  \\_____/\\___|\\__,_|\\__,_|\\___|_|  |_.__/ \\___/ \\__,_|_|  \\__,_|  _| |\n"
                + "  |___|                                                                |___|\n");
                System.out.println("=========================================================================================================================================\n");
                System.out.println("================================================   Leaderboard   ========================================================================\n");
                System.out.println("=========================================================================================================================================\n");
                while (!question.toLowerCase().contains("bye")) {
                        System.out.println("-1 Native leaderboard\n-2 Real Time Interactive Leaderboard (New updates!)\n");
                        System.out.println("=========================================================================================================================================\n");
                        String input = xx.nextLine();
                        switch (input) {
                                case "2": {
                                        System.out.println("\nNow showing you top traders......\n");
                                        Sleep.sleepInSeconds(2);                
                                        Leaderboard.openRealTimeSystemQueueInBrowser();
                                        break;
                                }
                                case "1": {
                                        Leaderboard.showRanking(a);
                                        break;
                                }
                                default:break;
                        }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }      
        }

        // option 10 -change user info
        public static void ChangeUserInfo(UserAuthenticationSystem a, String email) {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.print("  _____      _            _         _        __      \n" +
                        " |  __ \\    (_)          | |       (_)      / _|     \n" +
                        " | |__) | __ ___   ____ _| |_ ___   _ _ __ | |_ ___  \n" +
                        " |  ___/ '__| \\ \\ / / _` | __/ _ \\ | | '_ \\|  _/ _ \\ \n" +
                        " | |   | |  | |\\ V / (_| | ||  __/ | | | | | || (_) |\n" +
                        " |_|   |_|  |_| \\_/ \\__,_|\\__\\___| |_|_| |_|_| \\___/ \n");
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "=================================================     Security Settings    ==============================================================\n");
                while (!question.toLowerCase().contains("bye")) {
                System.out.println("\n-1 Reset Password\n-2 Reset Username\n");
                String input = xx.nextLine();
                switch (input) {
                        case "1": {
                        a.getUser(email).setPassword();
                        break;
                        }
                        case "2": {
                        System.out.print("\nKey in your desired username:\n>");
                        String newusername = xx.nextLine();
                        a.getUser(email).setUsername(newusername);
                        break;
                        }
                        default:
                        break;
                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
        }

        // options 5- notification setting
        /*
        * notificationgeneral;
        * notificationemail;
        * notificationthreshold;
        */
        public static void NotificationSetting(UserAuthenticationSystem a, String email) {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.print("  _   _       _   _  __ _           _   _             \n"
                        + " | \\ | |     | | (_)/ _(_)         | | (_)            \n"
                        + " |  \\| | ___ | |_ _| |_ _  ___ __ _| |_ _  ___  _ __  \n"
                        + " | . ` |/ _ \\| __| |  _| |/ __/ _` | __| |/ _ \\| '_ \\ \n"
                        + " | |\\  | (_) | |_| | | | | (_| (_| | |_| | (_) | | | |\n"
                        + " |_| \\_|\\___/ \\__|_|_| |_|\\___\\__,_|\\__|_|\\___/|_| |_|\n"
                        + "                                                      \n"
                        + "                                                      \n");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "===============================================     Notification Settings    ============================================================\n");
                while (!question.toLowerCase().contains("bye")) {
                System.out.println("\n-1 Pop up notification\n-2 Email notification\n-3 Trading Threshold Notification\n");
                String input = xx.nextLine();
                switch (input) {
                        case "1": {
                        System.out.println("\n[Default setting is enabled]");
                        System.out.println("\n-1 Disable\n-2 Enable\n");
                        System.out.println(
                                "=========================================================================================================================================");
                        String answer = xx.nextLine();
                        if (answer.equalsIgnoreCase("1")) {
                                a.getUser(email).setNotificationgeneral(false);
                                a.getUser(email).setRealnotificationgeneral(false);
                                System.out.println("\nPop Up Notification disabled!\n");
                        } else {
                                a.getUser(email).setNotificationgeneral(true);
                                a.getUser(email).setRealnotificationgeneral(true);
                                System.out.println("\nPop Up Notification enabled!\n");
                        }
                        break;
                        }
                        case "2": {
                        System.out.println("\n[Default setting is enabled]");
                        System.out.println("\n-1 Disable\n-2 Enable\n");
                        System.out.println(
                                "=========================================================================================================================================");
                        String answer = xx.nextLine();
                        if (answer.equalsIgnoreCase("1")) {
                                a.getUser(email).setNotificationemail(false);
                                System.out.println("\nEmail Notification disabled!\n");
                        } else {
                                a.getUser(email).setNotificationemail(true);
                                System.out.println("\nEmail Notification enabled!\n");
                        }
                        break;
                        }
                        case "3": {
                        System.out.println("\n[Default setting is disabled]");
                        System.out.println("\n-1 Disable\n-2 Enable [ Need to configure ]\n");
                        System.out.println(
                                "=========================================================================================================================================");
                        String answer = xx.nextLine();
                        if (answer.equalsIgnoreCase("1")) {
                                a.getUser(email).setNotificationthreshold(false);
                                System.out.println("\nThreshold Notification disabled!\n");
                        } else {
                                a.getUser(email).setThresholdCrossingValue();
                                System.out.println("\nThreshold Notification configured and enabled!\n");
                        }
                        break;
                        }
                        default:
                        break;
                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
        }

        // options 7 -trade
        public static void Trade(UserAuthenticationSystem a, String email) {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(" _____             _ _                  ______ _       _    __                     \n" +
                        "|_   _|           | (_)                 | ___ \\ |     | |  / _|                    \n" +
                        "  | |_ __ __ _  __| |_ _ __   __ _      | |_/ / | __ _| |_| |_ ___  _ __ _ __ ___  \n" +
                        "  | | '__/ _` |/ _` | | '_ \\ / _` |     |  __/| |/ _` | __|  _/ _ \\| '__| '_ ` _ \\ \n" +
                        "  | | | | (_| | (_| | | | | | (_| |     | |   | | (_| | |_| || (_) | |  | | | | | |\n" +
                        "  \\_/_|  \\__,_|\\__,_|_|_| |_|\\__, |     \\_|   |_|\\__,_|\\__|_| \\___/|_|  |_| |_| |_|\n" +
                        "                              __/ |                                                \n" +
                        "                             |___/                                                 ");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "======================================================     Trading Center    ============================================================\n");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "==========================================================    WARNING      ==============================================================\n");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "Direct Buy -> Traded only and only on market price\nNormal Buy -> Traded on desired price within participants\nListing Sell -> Traded on desired price within participants");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println("To protect market stakeholders, we disallow 1% threshold trading\n");
                System.out.println("\nHappy Trading!\n");

                while (!question.toLowerCase().contains("bye")) {
                System.out.print(
                        "\n-1 Direct Buy From EZTrade [Subject to availability]\n-2 Normal Buy [Subject to current demand] \n-3 Listing Sell [Free market]\n\n>");
                String input = xx.nextLine();
                switch (input) {
                        case "1": {
                        try { // direct buy
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.println(
                                        "=======================================================     Direct Buy    ===============================================================\n");
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Key in your desired stock name [ e.g. i) APEX ii) APM [S] ....]\n>");
                                String name = xx.nextLine();
                                System.out.print("Key in your number of shares to buy [ e.g. i) 100 ii) 69 ....]\n>");
                                String number = xx.nextLine();
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Confirm to perform buy operation? [Y/N] \n>");
                                String confirm = xx.nextLine();
                                confirm.toLowerCase();
                                if (confirm.contains("n")) {
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                break;
                                }
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                a.getUser(email)
                                        .buyFromSystem(new TransactionStock(name.toUpperCase(), Integer.parseInt(number), 0));
                                break;
                        } catch (Exception e) {
                                System.out.println("Operations could not be done. Please contact admin! ");
                                break;
                        }
                        }
                        case "2": { // normal buy
                        try {
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.println(
                                        "======================================================     Normal Buying    =============================================================\n");
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Key in your desired stock name [ e.g. i) APEX ii) APM [S] ....]\n>");
                                String name = xx.nextLine();
                                System.out.print("Key in your number of shares to buy [ e.g. i) 10 ii) 69 ....]\n>");
                                String number = xx.nextLine();
                                System.out
                                        .print("Key in your price per share to buy [ e.g. i) 0.95 ii) 69.5  iii) 69 ....]\n>");
                                String price = xx.nextLine();
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Confirm to perform buy operation? [Y/N] \n>");
                                String confirm = xx.nextLine();
                                confirm.toLowerCase();
                                if (confirm.contains("n")) {
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                break;
                                }
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                a.getUser(email).pushbuy(new TransactionStock(name.toUpperCase(), Integer.parseInt(number),
                                        Double.parseDouble(price)));
                                break;
                        } catch (Exception e) {
                                System.out.println("Operations could not be done. Please contact admin! ");
                                break;
                        }
                        }
                        case "3": { // listing sell
                        try {
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.println(
                                        "======================================================     Normal Listing    ============================================================\n");
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Key in your desired stock name [ e.g. i) APEX ii) APM [S] ....]\n>");
                                String name = xx.nextLine();
                                System.out.print("Key in your number of shares to sell [ e.g. i) 10 ii) 69 ....]\n>");
                                String number = xx.nextLine();
                                System.out
                                        .print("Key in your price per share to sell [ e.g. i) 0.95 ii) 69.5  iii) 69 ....]\n>");
                                String price = xx.nextLine();
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                System.out.print("Confirm to perform sell operation? [Y/N] \n>");
                                String confirm = xx.nextLine();
                                confirm.toLowerCase();
                                if (confirm.contains("n")) {
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                break;
                                }
                                System.out.println(
                                        "=========================================================================================================================================\n");
                                a.getUser(email).pushsell(new TransactionStock(name.toUpperCase(), Integer.parseInt(number),
                                        Double.parseDouble(price)));
                                break;
                        } catch (Exception e) {
                                System.out.println("Operations could not be done. Please contact admin! ");
                                break;
                        }
                        }
                        default:
                        System.out.println("\nBack to main menu~");
                        break;
                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
                }
        }

        public static void CancelTransaction(UserAuthenticationSystem a, String email) {
                String question = "";
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        " ___     _____                      _ _       _   _                ___  \n|__ \\   /  __ \\                    | | |     | | (_)              |__ \\ \n   ) |  | /  \\/ __ _ _ __   ___ ___| | | __ _| |_ _  ___  _ __       ) |\n  / /   | |    / _` | '_ \\ / __/ _ \\ | |/ _` | __| |/ _ \\| '_ \\     / / \n |_|    | \\__/\\ (_| | | | | (_|  __/ | | (_| | |_| | (_) | | | |   |_|  \n (_)     \\____/\\__,_|_| |_|\\___\\___|_|_|\\__,_|\\__|_|\\___/|_| |_|   (_)  \n");
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "=========================================================================================================================================");
                System.out.println(
                        "==========================================================    WARNING      ==============================================================\n");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println(
                        "                                We strongly discourage cancellation. It's on your own risk anyway.");
                System.out.println(
                        "=========================================================================================================================================\n");
                System.out.println("\nAre you sure to proceed to cancellation process? [ YES / NO ]\n");
                String line = xx.nextLine();
                line.toLowerCase();
                if (line.contains("no")) {
                return;
                }
                System.out.println("\nProceeding cancellation system.....\n");
                while (!question.toLowerCase().contains("bye")) {
                try { // direct buy
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.println(
                                "=====================================================     Cancellation    ===============================================================\n");
                        System.out.println(
                                "=========================================================================================================================================\n");
                        System.out.print("Key in your desired stock name for cancellation [ e.g. i) APEX ii) APM [S] ....]\n>");
                        String name = xx.nextLine();
                        System.out.print("Key in your number of shares of particular stock[ e.g. i) 100 ii) 69 ....]\n>");
                        String number = xx.nextLine();
                        System.out.print(
                                "Key in your price per share for the particular stock [ e.g. i) 0.95 ii) 69.5  iii) 69 ....]\n>");
                        String price = xx.nextLine();
                        System.out.println(
                                "=========================================================================================================================================\n");
                        a.getUser(email).cancelbuy(
                                new TransactionStock(name.toUpperCase(), Integer.parseInt(number), Double.parseDouble(price)));
                        break;
                } catch (Exception e) {
                        System.out.println("Operations could not be done. Please contact admin! ");
                        break;
                }

                }
                System.out.println("\n~Type <bye> to get out of here~");
                question = xx.nextLine();
        }
}
