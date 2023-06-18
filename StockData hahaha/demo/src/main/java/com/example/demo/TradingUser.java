package com.example.demo;

import java.awt.Desktop;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TradingUser extends UserInfo{
    public String username;
    public String password;
    private String email;
    public boolean consoleshow;
    public boolean notificationgeneral;
    public boolean notificationemail;
    public boolean notificationthreshold;
    public boolean realnotificationgeneral;
    public double thresholdloss;
    public double thresholdprofit;
    transient Scanner xx;
    public transient TimerTask currentTask;

    public TradingUser() {
    }
    
    // need to understand that everyday der log out, next time masuk need to call set starting balance manually
    // after register pass here the arguement,
    public TradingUser(String username, String password, String email,double accountBalance) {
        super();
        this.username = username;
        this.password = password;
        this.email=email;
        this.consoleshow=false;
        this.notificationgeneral=false;
        this.realnotificationgeneral=true;
        //email always true for default
        this.notificationemail=true;
        //notification for crossing threshold
        this.notificationthreshold=false;
        this.accountBalance=accountBalance;
        // <--------------------------
    }

    public boolean isNotificationemail() {
        return notificationemail;
    }

    public void setNotificationemail(boolean notificationemail) {
        this.notificationemail = notificationemail;
    }
    public void setConsoleshow(boolean a){
        this.consoleshow=a;
    }
    public boolean getConsoleshow(){
        return this.consoleshow;
    }
    public void setNotificationgeneral(boolean a){
        this.notificationgeneral=a;
    }
    
    public boolean getNotificationgeneral(){
        return this.notificationgeneral;
    }

    public boolean getNotificationthreshold(){
        return this.notificationthreshold;
    }
    
    public boolean isRealnotificationgeneral() {
        return realnotificationgeneral;
    }

    public void setRealnotificationgeneral(boolean realnotificationgeneral) {
        this.realnotificationgeneral = realnotificationgeneral;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email){
        this.email=email;
    }

    // return username
    

    // reset username
    public void setUsername(String username) {
        xx=new Scanner(System.in);
        System.out.println("\nPlease Key-in your current password to make a change to your username");
        String line = xx.nextLine();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String temp = this.username;
        if (encoder.matches(line,password)) {        
            this.username = username;
            System.out.println("\nUsername changed successfully!\n");
            // Add notification
            // Add email  --> notify new changes
            if (notificationgeneral) {
                Notification.showNotification("Changes Alert", "Your username has been changed successfully");
            }if(notificationemail){
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    // Run the email function in a separate thread
                    executor.execute(() -> {
                    EmailFunction.sendMail(this.email, "Recent changes to sensitive information on EZTrade",
                        "Changes on your username on EZTrade",
                        "Dear " + this.getUsername() +
                        ", we found out that you had made recent changes to your username!" +
                        "<p>Previous username: <b>" + temp + "</b></p><p>Latest username: <b>" + getUsername() +
                        "</b></p><p>Do check your account for more details!</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
            } 
        } else {
            System.out.println("\nWrong password! No access to change the username!\n");
            if(notificationgeneral)
                Notification.showNotification("Sensitive Information Alert", "Wrong password! No access to change the username!");
        }
    }

    // added security for set password
    public void setPassword() {
        xx=new Scanner(System.in);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("\nPlease Key-in your current password to make change to your password\n");
        String confirmation = xx.nextLine();                 
        if (encoder.matches(confirmation,password)) {
            System.out.println("\nPrivileged accessed!\n");
            System.out.println("Your new password: ");
            String gg = xx.nextLine();                
            this.password= encoder.encode(gg);
            //Add notification
            //Add email  --> notify new changes
            
            if (notificationgeneral) {
                Notification.showNotification("Sensitive Information Alert", "Your password has been changed successfully");
            }if(notificationemail){
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Recent changes to sensitive information on EZTrade",
                                "Changes on your password on EZTrade",
                                "Dear " + getUsername() +
                                ", we found out that you had made recent changes on your password!" +
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
            }
            System.out.println("\nYour password has been changed successfully!\n");
        }
        else {
            System.out.println("\nPrivileged not accessed!\n");
            if(notificationgeneral)
                Notification.showNotification("Sensitive Information Alert", "Privileged not accessed!");
        }
    }

    

    //a method that perform try buying of a specific stock (check whether if a buy order is pushed at the moment, will there be
    // a matching sell order? )
    // a method only for buyer usage.
    public static boolean promptBuy(TransactionStock buyOrder) {
        for (TransactionStock sellOrder : StockTradingSystem.sellOrders) {
            if (buyOrder.getEquitiesname().equalsIgnoreCase(sellOrder.getEquitiesname()) &&
                    buyOrder.getPricespershares() == sellOrder.getPricespershares() && buyOrder.getNumberofshares()
                    == sellOrder.getNumberofshares()) {
                return true; // Matching sell order found
            }
        }
        return false; // No matching sell order found
    }
    

    //need to set the threshold notification= true
    public void setNotificationthreshold(boolean a){
        this.notificationthreshold=a;
        if(a==true){
            setThresholdCrossingValue();
        }
    }

    //then set the crossing threshold;
    public void setThresholdCrossingValue(){
        xx=new Scanner(System.in);
        System.out.println("\nEnter your desired threshold profit : ");
        this.thresholdprofit=xx.nextDouble();
        System.out.println("\nEnter your desired threshold loss : ");
        this.thresholdloss=(-1)*xx.nextDouble();
        System.out.println("\nThreshold P&L has been set! "+"\nThreshold Profit : "+thresholdprofit+"\nThreshold Loss   : "+thresholdloss);
        if(notificationgeneral){
            Notification.showNotification("Info", "Threshold P&L has been set! "+"\nThreshold Profit : "+thresholdprofit+"\nThreshold Loss   : "+thresholdloss);
        }
        if(notificationemail){
            ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Threshold P&L changed! ",
                                "Dear " + getUsername() +
                                ", you had set new P&L Threshold !" +
                                "<p>Threshold Profit : "+thresholdprofit+"</p>"+
                                "<p>Threshold Loss   : "+thresholdloss+"</p>"+
                                "<p><strong>Perform your wise actions & decisions.</strong></p>"+
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
        }
    }

    //threshold crossing for daily trading PL (not cummulative der is daily der)
    // note it is for daily PL threshold
    //input ur desired loss and profit ( once reached will notify u both notification, console, and email)
    public void checkThresholdCrossingAlert(){
        if(getDifferenceForDay()<thresholdloss){
            if(consoleshow){
                System.out.println("\n\nWarning!\nYour PL threshold had reached !\nTargeted threshold: "+
                thresholdloss+"\nCurrent loss :"+getDifferenceForDay()+"\n");
            }
            if(notificationgeneral){
                Notification.showNotification("Warning", "Your PL threshold had reached !\nTargeted threshold: "+
                thresholdloss+"\nCurrent loss :"+getDifferenceForDay());
            }
            if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Trading Profit & Loss Threshold Reached Beyond ! ",
                                "Dear " + getUsername() +
                                ", your PL threshold had reached !" +
                                "<p>Targeted threshold : "+thresholdloss+"</p>"+
                                "<p>Current loss : "+getDifferenceForDay()+"</p>"+
                                "<p><strong>Perform your wise actions & decisions.</strong></p>"+
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
            }
        }
        else if(getDifferenceForDay()>thresholdprofit){
            if(consoleshow){
                System.out.println("\n\nCongratulations trader!\nYour PL threshold had reached !\nTargeted threshold: "+
                thresholdprofit+"\nCurrent Profit :"+getDifferenceForDay());
            }
            if(notificationgeneral){
                Notification.showNotification("Info", "Your PL threshold had reached !\nTargeted threshold: "+
                thresholdprofit+"\nCurrent Profit :"+getDifferenceForDay());
            }
            if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Trading Profit & Loss Threshold Reached Beyond ! ",
                                "Dear " + getUsername() +
                                ", your PL threshold had reached !" +
                                "<p>Targeted threshold : "+thresholdprofit+"</p>"+
                                "<p>Current Profit : "+getDifferenceForDay()+"</p>"+
                                "<p><strong>Perform your wise actions & decisions.</strong></p>"+
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
            }
        }
    }


    
    // try buying
    // use new operator
    // ensure balance is >0
    public void pushbuy(TransactionStock a) {
        // update
        getHeldStocks(1);
        getListedStocks(1);
        if(a.numberofshares<100){
            if(consoleshow){
                System.out.println("\nMinimum 100 shares are required!\n");
            }
            if(notificationgeneral)
                Notification.showNotification("Warning", "\\n" + //
                        "Mininum 100 shares are required!");
            return;
        }
        // case 1- buy for the very first time
        // case 2- buy for second time (top up the shares, but u need to separate cause
        // differnt price bought at different time, even u share the same equities
        // shares,
        // in this case we are applying FiFo-rules, when selling, we sell out the oldest
        // one first
        if (getAccountBalance() < a.getTotalprices()) {
            if(consoleshow){
                System.out.println("\nBalance will become negative! You will be disqualified if persisted.");
            }
            if(notificationgeneral)
                Notification.showNotification("Warning", "Balance will become negative! You will be disqualified if persisted.");
        }
        // check for validity of name (ensure valid stock) & order since we cant excceed
        // 1% of market price to ensure fair trades
        boolean status = false;
        for (Stock x : UpdateAndStoreLocal.stockDataNameBased) {
            if (x.getName().equalsIgnoreCase(a.equitiesname)) {
                status = true;
                double onePercent = x.getLastDone() * 0.01; // if exceed or smaller than 1% from market rate, then
                                                            // return
                if (a.getPricespershares() > x.getLastDone() + onePercent || a.getPricespershares() < x.getLastDone() - onePercent) {
                    if(consoleshow){
                        System.out.println("\n"+this.username + " , alert that to ensure market fairness, overprice & underprice trading [exceed 1%] is detected & aborted.");
                    }
                    if(notificationgeneral)
                        Notification.showNotification("Warning", this.username + " , alert that to ensure market fairness, overprice & underprice trading [exceed 1%] is detected & aborted.");
                    return;
                }
            }
        }
        // if the stock name is not found, directly return
        if (status != true) {
            if(consoleshow){
                System.out.println("\nNo such stocks! Please input the proper stock name!");
            }
            if(notificationgeneral){
                Notification.showNotification("Alert", "No such stocks! Please input the proper stock name!");
            }
            return;
        }
        // pass the name test & prices acceptable test

        // upload to system
        // Add the buy order to the automatching algorithmz
        a.setUser(this);
        // .....
        //check whether the system permit 500 shares order (initial trading period or not)
        //if the transaction is >500 and it is not initial period, then cancel buylist, return (included inside addbuyorder method),
        //else continue the method
        if(StockTradingSystem.addBuyOrder(a)==false){ 
            if(consoleshow){
                System.out.println("\nOrder canceled for user : " + getUsername() + "' as it exceeds the 500 shares per order.\n");
            }
            if(notificationgeneral){
                Notification.showNotification("Warning", "\nOrder canceled for user : " + getUsername() + "' as it exceeds the 500 shares per order.\n");              
            }
            return;
        }
        //if true need to configure the bug
        StockTradingSystem.buyOrders.remove(a);

        //perform promptbuying to check whether there is matching order
        // if there is skip the condition asking and direct the code to below ()
        // if there is not matching order, perform asking on the user
        // case 1: to continue to the buying (queue on the system and wait until market closes)
        // case 2: to cancel the current buying ( perform a better buying either matching buy from list, or  buying from lot pools)
        // case 3: direct buying from lot pools       
        // ---logic here

        if(promptBuy(a)==false){ //the prompt buy didnt find any matching sell order
            xx=new Scanner(System.in);
            System.out.println("\nCurrently, there is no matching order found for your purchasing order\n"+
            "Option 1: Continue to proceed to buy order (queue and wait until market closes or there is existing satisfying order)\n"+
            "Option 2: Cancel current operation\n"+
            "Option 3: Buy from 500-lot pools (if available) & Buying from System will only considered the current market price\n");
            System.out.println("Key in the desired number option [e.g. \"1\" for option 1]");
            Notification.showNotification("Alert", "\nCurrently, there is no matching order found for your purchasing order\n"+
            "Option 1: Continue to proceed to buy order (queue and wait until market closes or there is existing satisfying order)\n"+
            "Option 2: Cancel current operation\n"+
            "Option 3: Buy from 500-lot pools (if available) & Buying from System will only considered the current market price\n");
            int input=xx.nextInt();
            switch(input){
                case(1):{
                    System.out.println("\n[Your Choice] : Continue proceed to buy order\n");
                    Notification.showNotification("Warning", "\n[Your Choice:]Continue proceed to buy order\n");
                    //continue with the order
                    break;
                }
                case(2):{
                    System.out.println("\n[Your Choice] : Operation cancelled\n");
                    Notification.showNotification("Warning", "\n[Your Choice:]Operation cancelled\n");
                    //balik rumah cancel operation
                    return;
                }
                case(3):{
                    System.out.println("\n\n[Your Choice] : Buying from System will only considered the current market price\n"+
                                        "[Alert]Your current open price for buying will be overridden!\n");
                    Notification.showNotification("Warning", "\n\n[Your Choice:]Buying from System will only considered the current market price\n"+
                    "[Alert]Your current open price for buying will be overridden!\n");             
                    buyFromSystem(a);
                    return;}
                    //buy from system and quit method
                default:return; //cancel operation for other input
            }
        }
        //prompt buy found the matching sell order OR the user want to continue and wait
        a.activity="Buy Order";
        StockTradingSystem.addBuyOrder(a);
        addBuyList(a); 

        // bags configured
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+this.username + " :" + " Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
                + " for " + a.getPricespershares() + " per share is pending in queue !");        
        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + " Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is pending in queue !");
                
        }if(notificationemail){
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Buying on pending",
                                "Dear " + getUsername() +
                                ", your order of buying " + a.getEquitiesname() + " for " + a.getNumberofshares()
                                + " for " + a.getPricespershares() + " per share is pending in queue !" +
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
        }
        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),a.getPricespershares());
        copy.activity = "Push system to buy";
        tradeHistorys.add(copy);
        // update priority queue and arraylist
        getHeldStocks(1);
        getBuyList(1);
    }

    // buy successful
    // must receive arguement of same thing(dont use new operator)
    public void buysuccessful(TransactionStock a) {
        // bags configured after success
        // update portfolio
            a.setUser(this);            
            addHeldStocks(a);            
            removeBuyList(a);        
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+username+" : Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is successful ! ");
        if(consoleshow){
            System.out.println("\n\nYour stock purchase of "+a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares()+" per share is successful !");
            System.out.println("Time taken for stock purchase :" + a.getdifferencebetweentime());
            // configure userinfo
            System.out.println("======================================================     Your Profile    ==============================================================\n");
            System.out.println("Account balance before      : RM " + getAccountBalance());
        }            
        updateAccountBalance(-(a.getTotalprices()));
        if(consoleshow){
            System.out.println("Remaining account balance   : RM " + getAccountBalance());
            // update user info & P&L
            // update P&L points as well as cumulative P&L
            System.out.println("Difference for this trade : RM 0.00");
            System.out.println("P/L points for this trade :    0.0");
            System.out.println("P/L points for this day     :   " + getPlPointsofday());
            System.out.println("Cumulative P/L points       :   " + getcumulativePLPoints());
            System.out.println("=========================================================================================================================================\n");

        }

        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + "Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is successful !");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    EmailFunction.sendMail(email, "Trading activity on EZTrade",
                            "Order purchased successfully",
                            "Dear " + getUsername() +
                            ", your order of buying " + a.getEquitiesname() + " for " + a.getNumberofshares()
                            + " for " + a.getPricespershares() + " per share is successful !" +
                            "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                            "</b></p><p>Do check your account for more details!</p>"+
                            "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                });
                // Shutdown the executor
                executor.shutdown();
        }
        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),a.getPricespershares());
        copy.activity = "Buy Successful! ";
        copy.setTimeforactivity2V();
        tradeHistorys.add(copy);
        // update the priority queue & arraylist
        getHeldStocks(1);
        getBuyList(1);
        // update end of day balance;

        setEndOfDayBalance(super.accountBalance);
        if (super.accountBalance < 0) {
            if(consoleshow){
                System.out.println("\nYou will be disqualified if account balance remains negative! ");
            }
            if(notificationgeneral){
                Notification.showNotification("Alert", "You will be disqualified if account balance remains negative! ");
            }
        }
        //checkQualificationForNextDay();
    }
    
    public void buyFromSystem(TransactionStock a){
        //should have ignore the input of price per share from the user input
        if(a.numberofshares<100){
            if(consoleshow){
                System.out.println("\nMinimum 100 shares are required!\n");
            }
            if(notificationgeneral)
                Notification.showNotification("Warning", "\\n" + //
                        "Mininum 100 shares are required!");
            return;
        }
        a.setUser(this);
        boolean status = false;
        double marketpricepershare=0;
        for (Stock x : UpdateAndStoreLocal.stockDataNameBased) {
            if (x.getName().equalsIgnoreCase(a.equitiesname)) {
                status = true;
                marketpricepershare=x.getLastDone();
            }
        }
        // if the stock name is not found, directly return
        if (status != true) {       
            if(consoleshow){
                System.out.println("\nNo such stocks! Please input the proper stock name!");
            }
            if(notificationgeneral){
                Notification.showNotification("Alert", "No such stocks! Please input the proper stock name!");
            }
            return;
        }
        //handle exception of keying wrong name
        //after that update the price pershare as well as total price
        a.updatemarketprice(marketpricepershare);
        StockTradingSystem.buyFromLotPool(a);
        
    }

    //buy successful from system (simplified implementation)
    public void buysuccessfulfromsystem(TransactionStock a) {
        // bags configured after success
        // update portfolio
            a.setUser(this);  
            addHeldStocks(a);  
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+username+" : Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share FROM THE SYSTEM is successful ! ");
        if(consoleshow){
            System.out.println("\n\nYour stock purchase of "+a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares()+" per share FROM THE SYSTEM is successful !");
            System.out.println("Time taken for stock purchase :" + a.getdifferencebetweentime());
            // configure userinfo
            System.out.println("======================================================     Your Profile    ==============================================================\n");
            System.out.println("Account balance before      : RM " + getAccountBalance());
        }            
        updateAccountBalance(-(a.getTotalprices()));
        if(consoleshow){
            System.out.println("Remaining account balance   : RM " + getAccountBalance());
            // update user info & P&L
            // update P&L points as well as cumulative P&L
            System.out.println("Difference for this trade : RM 0.00");
            System.out.println("P/L points for this trade :    0.0");
            System.out.println("P/L points for this day     :   " + getPlPointsofday());
            System.out.println("Cumulative P/L points       :   " + getcumulativePLPoints());
            System.out.println("=========================================================================================================================================\n");

        }

        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + "Buy " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share FROM THE SYSTEM is successful !");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    EmailFunction.sendMail(email, "Trading activity on EZTrade",
                            "Order purchased FROM THE SYSTEM successfully",
                            "Dear " + getUsername() +
                            ", your order of buying " + a.getEquitiesname() + " for " + a.getNumberofshares()
                            + " for " + a.getPricespershares() + " per share FROM THE SYSTEM is successful !" +
                            "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                            "</b></p><p>Do check your account for more details!</p>"+
                            "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                });
                // Shutdown the executor
                executor.shutdown();
        }
        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),a.getPricespershares());
        copy.activity = "Buy Successful! FROM THE SYSTEM";
        copy.setTimeforactivity2V();
        tradeHistorys.add(copy);
        // update the priority queue & arraylist
        getHeldStocks(1);
        getBuyList(1);
        // update end of day balance;

        setEndOfDayBalance(super.accountBalance);
        if (super.accountBalance < 0) {
            if(consoleshow){
                System.out.println("\nYou will be disqualified if account balance remains negative! ");
            }
            if(notificationgeneral){
                Notification.showNotification("Alert", "You will be disqualified if account balance remains negative! ");
            }
        }
      //  checkQualificationForNextDay();
    }


    // cancel buy
    // must receive arguement of same thing(dont use new operator)
    public void cancelbuy(TransactionStock b) {    
        for (TransactionStock transaction : buyLists) {
        if (transaction.getEquitiesname().equals(b.getEquitiesname()) &&
            transaction.getNumberofshares() == b.getNumberofshares() &&
            transaction.getPricespershares() == b.getPricespershares()) {
            b=transaction;
            break;
        }
        } 
        TransactionStock a = b; // Create a new variable
        a.setUser(this);   
        StockTradingSystem.removeBuyOrder(a);
        removeBuyList(a);
        getBuyList(1);
        if(consoleshow){
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+username+ " : Order of  " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is cancelled ! ");
            System.out.println("Time cancelled since stock purchase :" + a.getdifferencebetweentime());
            // configure userinfo
            System.out.println("======================================================     Your Profile    ==============================================================\n");
            System.out.println("Account balance before     : RM" + getAccountBalance());
            System.out.println("Remaining account balance  : RM" + getAccountBalance());
            System.out.println("=========================================================================================================================================\n");
        }

        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + "Order of  " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is cancelled ! ");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    EmailFunction.sendMail(email, "Trading activity on EZTrade",
                            "Buy Order is cancelled",
                            "Dear " + getUsername() +
                            ", your order of buying " + a.getEquitiesname() + " for " + a.getNumberofshares()
                            + " for " + a.getPricespershares() + " per share is being cancelled !" +
                            "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                            "</b></p><p>Do check your account for more details!</p>"+
                            "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                });
                // Shutdown the executor
                executor.shutdown();
        }
        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),
                a.getPricespershares());
        copy.activity = "Cancel Buying";        
        copy.setTimeforactivity2V();
        tradeHistorys.add(copy);
        // update the priority queuea and arraylist
        getHeldStocks(1);
        getBuyList(1);
    }
    // 1. if hold the stocks, the price will fluactate, thus whenever callout
    // get value of holdings method, need to update the price first by setting , set
    // price (using static
    // to get the market price)

    public void pushsell(TransactionStock a) {
        // update also
        getHeldStocks(1);
        getListedStocks(1);
        // check for validity of name (ensure valid stock) & order since we cant excceed
        // 1% of market price to ensure fair trades
        boolean status = false;
        for (Stock x : UpdateAndStoreLocal.stockDataNameBased) {
            if (x.getName().equals(a.equitiesname)) {
                status = true;
                double onePercent = x.getLastDone() * 0.01; // if exceed or smaller than 1% from market rate, then
                                                            // return
                if (a.getPricespershares() > x.getLastDone() + onePercent || a.getPricespershares() < x.getLastDone() - onePercent) {
                    if(consoleshow){
                        System.out.println("\n"+this.username+ " ,alert that to ensure market fairness, overprice & underprice trading [exceed 1%] is detected & aborted.");
                    }
                        if(notificationgeneral)
                            Notification.showNotification("Warning", this.username + " , alert that to ensure market fairness, overprice & underprice trading [exceed 1%] is detected & aborted.");
                return;
                }
            }
        }
        // if the stock name is not found, directly return
        if (status != true) {
            if(consoleshow){
                System.out.println("\nNo such stocks in Bursa Malaysia to be sold! ");
            }
            if(notificationgeneral)
                Notification.showNotification("Warning", "No such stocks in Bursa Malaysia to be sold! ");
            return;
        }
        // pass the name test & prices acceptable test
        int tempshares = 0;
        // check first if meet requirement to sell or not
        // number of shares available >= list to sell
        for (TransactionStock x : heldStockss) {
            if (x.getEquitiesname().equals(a.getEquitiesname())) {
                tempshares += x.getNumberofshares();
            }
        }
        // if not enouf, then return
        if (tempshares < a.getNumberofshares()) {
            if(consoleshow){
                System.out.println("\nNot enough stocks in portfolio to be sold! ");
            }
            if(notificationgeneral)
                Notification.showNotification("Warning", "Not enough stocks in portfolio to be sold! ");
            return;
        }
        // pass number of shares available test

        // upload to system
        // Add the sell order to the automatching algorithm
        a.setUser(this);        
        if(StockTradingSystem.addSellOrder(a)==false){ 
            if(consoleshow){
                System.out.println("\nSales canceled for user : " + getUsername() + "' as it exceeds the 500 shares per sale.\n");
            }
            if(notificationgeneral){
                Notification.showNotification("Warning", "\nSales canceled for user : " + getUsername() + "' as it exceeds the 500 shares per sale.\n");              
            }
            return;
        }
        StockTradingSystem.sellOrders.remove(a);
       
        StockTradingSystem.addSellOrder(a);
        addListedStocks(a);
        a.activity="Sell Order";
        // .....
        // bags configured
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+this.username + " :" + " Selling " + a.getEquitiesname() + " for " + a.getNumberofshares()
                + " for " + a.getPricespershares() + " per share is pending in queue !");

        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + " Selling " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is pending in queue !");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Selling on pending",
                                "Dear " + getUsername() +
                                ", your order of selling " + a.getEquitiesname() + " for " + a.getNumberofshares()
                                + " for " + a.getPricespershares() + " per share is pending in queue !" +
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
        }

        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),
                a.getPricespershares());
        copy.activity = "Push system to sell";
        tradeHistorys.add(copy);
        // update priority queue and arraylist
        getHeldStocks(1);
        getListedStocks(1);
    }
    // sell successful
    // must receive arguement of same thing(dont use new operator)
    public void sellsuccessful(TransactionStock a) {
        a.setUser(null);
        // bags configured after success
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+username+" : Sold " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is successful !");
            removeListedStocks(a);        
        if(consoleshow){
            System.out.println("\n\nYour stock sold for "+a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares()+" per share");
            System.out.println("Time taken for stock sold :" + a.getdifferencebetweentime());
        }
        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + " Sell " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is successful !");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    EmailFunction.sendMail(email, "Trading activity on EZTrade",
                            "Order sell successfully",
                            "Dear " + getUsername() +
                            ", your order of selling " + a.getEquitiesname() + " for " + a.getNumberofshares()
                            + " for " + a.getPricespershares() + " per share is successful !" +
                            "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                            "</b></p><p>Do check your account for more details!</p>"+
                            "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                });
                // Shutdown the executor
                executor.shutdown();
        }

        // configure userinfo
        // loop till the numberofshares satisfied,calculate the costbasis alongside
        int tempnumberofsharesrequired = 0;
        double costbasis = 0;
        for (int i = 0; i < heldStockss.size(); i++) {
            if (heldStockss.get(i).getEquitiesname().equalsIgnoreCase(a.getEquitiesname())) {
                if (tempnumberofsharesrequired + heldStockss.get(i).getNumberofshares() > a.getNumberofshares()) {
                    int differencesharesleft = a.getNumberofshares() - tempnumberofsharesrequired;
                    costbasis += (heldStockss.get(i).getPricespershares() * differencesharesleft);
                    // done configuring cost basis
                    // now configure the heldstock in priority queue since array is not in memory
                    // everytime
                    // remove the priority queue element so that it updates in arraylist
                    TransactionStock x = heldStockss.get(i);
                    removeHeldStocks(x);
                    x.numberofshares -= differencesharesleft;
                    addHeldStocks(x);
                    break;
                    // break the fifo
                } else {
                    costbasis += heldStockss.get(i).getTotalprices();
                    tempnumberofsharesrequired += heldStockss.get(i).getNumberofshares();
                    removeHeldStocks(heldStockss.get(i));
                    i--;
                }
            }
        }
        if(consoleshow){
        // update user info & P&L
            System.out.println("======================================================     Your Profile    ==============================================================\n");
            System.out.println("Account balance before    : RM" + getAccountBalance());
        }
        // update account balance & update the difference for day and cumulative
        // differecne
        updateAccountBalance(finddifferenceforeachtrade(costbasis, a.getTotalprices()));        
        double differencewow=(a.getTotalprices() - costbasis);        
        double PLtrade= (differencewow/getStartingBalance())*100;
        plPoints+=PLtrade;
        cumulativePLPoints+=PLtrade;
        
        if(consoleshow){
            System.out.println("Remaining account balance : RM" + getAccountBalance());
            // update P&L points as well as cumulative P&L
            System.out.println("Difference for this trade : RM" + (a.getTotalprices() - costbasis));
            System.out.println("P/L points for this trade : " + PLtrade);
            System.out.println("P/L points for this day   : " + getPlPointsofday());
            System.out.println("Cumulative P/L points     : " + getcumulativePLPoints());
            System.out.println("=========================================================================================================================================\n");

        }
        //individually calculate the difference
        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),
                a.getPricespershares());
        copy.activity = "Sell successful";        
        copy.setTimeforactivity2V();
        copy.setDifference(differencewow);
        copy.setPLtrade(PLtrade);
        tradeHistorys.add(copy);
        // update priority queue and arraylist
        getHeldStocks(1);
        getListedStocks(1);
        // update end of day balance;
        setEndOfDayBalance(super.accountBalance);
        //checkQualificationForNextDay();
        if(notificationthreshold){
            checkThresholdCrossingAlert();
        }
    }

    // cancel sell
    // must receive arguement of same thing(dont use new operator)
    public void cancelsell(TransactionStock b) {  
        for (TransactionStock transaction : listedStockss) {
        if (transaction.getEquitiesname().equals(b.getEquitiesname()) &&
            transaction.getNumberofshares() == b.getNumberofshares() &&
            transaction.getPricespershares() == b.getPricespershares()) {
            b=transaction;
            break;
        }
        }
        TransactionStock a= b;  
        a.setUser(this);   
        StockTradingSystem.removeSellOrder(a);
        removeListedStocks(a);
        getListedStocks(1);        
        if(consoleshow){
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] "+username+" : Sell cancelled !");
            System.out.println("Time cancelled since stock listed :" + a.getdifferencebetweentime());
            // configure userinfo
            System.out.println("======================================================     Your Profile    ==============================================================\n");
            System.out.println("Account balance before    : RM" + getAccountBalance());
            System.out.println("Remaining account balance : RM" + getAccountBalance());
            System.out.println("=========================================================================================================================================\n");
        }
        if(notificationgeneral){
            Notification.showNotification("Info", this.username + " :" + "Selling of  " + a.getEquitiesname() + " for " + a.getNumberofshares()
            + " for " + a.getPricespershares() + " per share is cancelled ! ");
            
        }if(notificationemail){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        EmailFunction.sendMail(email, "Trading activity on EZTrade",
                                "Sell Order is cancelled",
                                "Dear " + getUsername() +
                                ", your order of selling " + a.getEquitiesname() + " for " + a.getNumberofshares()
                                + " for " + a.getPricespershares() + " per share is being cancelled !" +
                                "<p>If you believe that this is not your action, contact us immediately to suspend your account!</p>"+
                                "</b></p><p>Do check your account for more details!</p>"+
                                "<p>at  "+ ClockFunction.getStringCurrentTime()+"</p>");
                    });
                    // Shutdown the executor
                    executor.shutdown();
        }

        TransactionStock copy = new TransactionStock(a.getEquitiesname(), a.getNumberofshares(),
                a.getPricespershares());
        copy.activity = "Cancel selling";        
        copy.setTimeforactivity2V();
        tradeHistorys.add(copy);
        // update priority queue and arraylist
        getHeldStocks(1);
        getListedStocks(1);
    }

    public boolean checkPassword(String a) {
        if(this.password.equals(a)){
            return true;
        }
        else{
            return false;
        }
    }

    //update the user profile for html
    public void updateRealTimeUserProfile() {        
        // Initialize Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("static/dashboard/");
        templateEngine.setTemplateResolver(templateResolver);
        // Prepare the template context and set the variable
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("accountBalance", accountBalance);
        context.setVariable("plPoints", getPlPointsofday());
        context.setVariable("cumulativePLPoints", getcumulativePLPoints());
        context.setVariable("StartingBalance", StartingBalance);
        context.setVariable("differenceforday", differenceforday);
        context.setVariable("cumulativeDifference", cumulativeDifference);
        context.setVariable("qualifiedForNextDay",checkQualificationForNextDay());
        context.setVariable("heldStockss", heldStockss);
        context.setVariable("listedStockss", listedStockss);
        context.setVariable("buyLists", buyLists);
        context.setVariable("tradeHistorys", tradeHistorys);

        // Process the template with the context
        String processedHtml = templateEngine.process("dashboard.html", context);

        try {
            // Write the processed HTML to the initial template HTML file
            String templatePath = "demo\\src\\main\\resources\\static\\dashboard\\dashboardfinal.html";
            FileWriter writer = new FileWriter(templatePath);
            writer.write(processedHtml);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //open the user profile webpage
    public void openRealTimeUserProfile() {
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:5500/demo/src/main/resources/static/dashboard/dashboardfinal.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void RenderHTMLuserprofileWebsite(boolean a){
        //update periodically the profile html for 1 min
        Timer timer = new Timer();
        if(a==true){    
        currentTask = new TimerTask() {
                @Override
                public void run() {
                    updateRealTimeUserProfile();
                }
            };
        timer.schedule(currentTask, 0, 1* 60 * 1000);
        }
        else{
            if (currentTask != null) {
                currentTask.cancel();
            }
            timer.cancel();
        }
    }

    
}   


