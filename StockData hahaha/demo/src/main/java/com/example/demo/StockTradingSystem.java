package com.example.demo;

import java.time.LocalTime;
import java.awt.Desktop;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;



public class StockTradingSystem implements Serializable {
    public static List<TransactionStock> buyOrders = new ArrayList<>();
    public static List<TransactionStock> sellOrders = new ArrayList<>();
    private static Thread matchingThread;
    private static boolean initialTradingPeriod = true;
    public static Map<String, Integer> lotPool = new HashMap<>();
    public static StockDataPriorityQueue<TransactionStock> temp=new StockDataPriorityQueue<>(Comparator.comparing(TransactionStock::getdateRAW, Comparator.reverseOrder()));
    public static ArrayList<TransactionStock> systemqueue=new ArrayList<>(); 
    public static TimerTask currentTask2;
    public static TimerTask currentTask1;
    public static TimerTask currentTask3;


    public static void setInitialTradingPeriod(boolean initialTradingPeriod) {
        StockTradingSystem.initialTradingPeriod = initialTradingPeriod;
    }

    public static boolean addBuyOrder(TransactionStock buyOrder) {
        if (initialTradingPeriod || buyOrder.getNumberofshares() <= 500) {
            buyOrders.add(buyOrder);
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean addSellOrder(TransactionStock sellOrder) {
        if (initialTradingPeriod || sellOrder.getNumberofshares() <= 500) {
            sellOrders.add(sellOrder);
            return true;
        } else {
            return false;
        }
    }
    
    //direct Buy From System methods @ market price (subject to lot pool availability)
    public static boolean buyFromLotPool(TransactionStock buyOrder) {
        String stockName = buyOrder.getEquitiesname();
        int numberOfShares = buyOrder.getNumberofshares();

        if (initialTradingPeriod || buyOrder.getNumberofshares() <= 500) {
            if (lotPool.containsKey(stockName) && lotPool.get(stockName) >= numberOfShares) {
                lotPool.put(stockName, lotPool.get(stockName) - numberOfShares);
                TradingUser user = buyOrder.getUser();
                user.buysuccessfulfromsystem(buyOrder);
                return true;
            } else {
                if(buyOrder.getUser().consoleshow){
                    System.out.println("\n[SYSTEM] : 500-lot pool doesn't have the specific stock or the number of share for the stock is depleted in System. Stay tuned !\n");
                }
                if(buyOrder.getUser().notificationgeneral){
                    Notification.showNotification("Warning", "\n500-lot pool doesn't have the specific stock or the number of share for the stock is depleted in System. Stay tuned !\n");              
                }
                return false;
            }
        
        } else {
            if(buyOrder.getUser().consoleshow){
                System.out.println("\n[SYSTEM] : Order from System canceled for user : " + buyOrder.getUser().getUsername() + "' as it exceeds the 500 shares per order.\n");
            }
            if(buyOrder.getUser().notificationgeneral){
                Notification.showNotification("Warning", "\nOrder from System canceled for user : " + buyOrder.getUser().getUsername() + "' as it exceeds the 500 shares per order.\n");              
            }
            return false;
        }
        
    }

    public static void StartOfPeriod(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> UpdateAndStoreLocal.UpdateStock());
        
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] Stock System initializing....");
        //wait for stock update for 20 seconds (保守估计)
        Sleep.sleepInSeconds(20);        
        // if API doesnt respond then quit the programme
        if(UpdateAndStoreLocal.stockDataNameBased.size()==0){
            System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : Stock Price API has issues currently, try restarting programme.........");
            executor.shutdown();
            return;
        }
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : Stock Data updated successfully.........");
       // replenishLotPool();
        startMatching();
    }

    public static void startMatching() {
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : Trading Hour has started!");
        Notification.showNotification("Reminder","Trading Hour has started!" );
    
        matchingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    matchOrders();
                    Thread.sleep(2000); // Add a 2-second delay between iterations
                } catch (InterruptedException e) {
                    // Handle the InterruptedException
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        matchingThread.start();
    }
    
    public static void stopMatching() {
        if (matchingThread != null) {
            System.out.println("[SYSTEM] : Out of Trading Hour!");
            Notification.showNotification("Reminder","Out of Trading Hour!" );
            matchingThread.interrupt();
        }
        
    }

    private static void matchOrders() {
        for (TransactionStock buyOrder : buyOrders) {
            for (TransactionStock sellOrder : sellOrders) {
                if (buyOrder.getEquitiesname().equalsIgnoreCase(sellOrder.getEquitiesname()) &&
                        buyOrder.getPricespershares() == sellOrder.getPricespershares() && buyOrder.getNumberofshares()
                        == sellOrder.getNumberofshares()) {
                    TradingUser buyer = buyOrder.getUser();
                    TradingUser seller = sellOrder.getUser();
                    if(buyer==seller){
                        return;
                    }
                    buyer.buysuccessful(buyOrder);
                    seller.sellsuccessful(sellOrder);
                    buyOrders.remove(buyOrder);
                    sellOrders.remove(sellOrder);
                    return;  // Exit the method after finding the first matching pair
                }
            }
        }
    }

    //replenish the pool manually for admin to call the method...
    @SuppressWarnings("unused")
    public static void replenishLotPool() {
        for (Stock stock : UpdateAndStoreLocal.stockDataNameBased) {
            lotPool.put(stock.getName(), 500000);
        }
        //replenish 500k shares =500 lot
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : 500-Lot pool replenished!");
        Notification.showNotification("Reminder","500-Lot pool replenished!");
    }


    //for admin to visualize the pool manually 
    public static void showLotPoolShares() {
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : Available Shares in Lot Pool:");
        for (Map.Entry<String, Integer> entry : lotPool.entrySet()) {
            String stockName = entry.getKey();
            int availableShares = entry.getValue();
            String output = String.format("%-10s: %d", stockName, availableShares);
            System.out.println(output);
        }
    }

    public static void EndOfPeriod() {
        StockTradingSystem.stopMatching();
        for (TransactionStock buyOrder : buyOrders) {
            TradingUser buyer = buyOrder.getUser();
            buyer.cancelbuy(buyOrder);
        }
        for (TransactionStock sellOrder : sellOrders) {
            TradingUser seller = sellOrder.getUser();
            seller.cancelsell(sellOrder);
        }
        buyOrders.clear();
        sellOrders.clear();
        System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] SYSTEM : Trading period closed , all pending orders are cancelled respectively!\n");
        Notification.showNotification("Reminder","\nTrading period closed , all pending orders are cancelled respectively! \n" );
    }

    //show current system buyorders
    public static void displayBuyOrders() {
        System.out.println("\n[SYSTEM] : Current Buy Orders in system:");
        for (TransactionStock buyOrder : buyOrders) {
            System.out.println(buyOrder.toStringWithDateAndType());
        }
        System.out.println();
    }

    //show current system sellorders
    public static void displaySellOrders() {
        System.out.println("\n[SYSTEM] : Current Sell Orders in system:");
        for (TransactionStock sellOrder : sellOrders) {
            System.out.println(sellOrder.toStringWithDateAndType());
        }
        System.out.println();
    }
    
    //update the buy and sellorders for html
    public static void updateRealTimeSystemQueue() {        
        temp.addAll(buyOrders);
        temp.addAll(sellOrders);
        systemqueue.clear();
        while(!temp.isEmpty()){
            systemqueue.add(temp.poll());
        }
        // Initialize Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("static/sortabletable/demo/");
        templateEngine.setTemplateResolver(templateResolver);
        // Prepare the template context and set the variable
        Context context = new Context();
        context.setVariable("systemqueue", systemqueue);
        // Process the template with the context
        String processedHtml = templateEngine.process("buysellsystemqueue.html", context);

        try {
            // Write the processed HTML to the initial template HTML file
            String templatePath = "demo\\src\\main\\resources\\static\\sortabletable\\demo\\buysellsystemqueuefinal.html";
            FileWriter writer = new FileWriter(templatePath);
            writer.write(processedHtml);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openRealTimeSystemQueueInBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:5500/demo/src/main/resources/static/sortabletable/demo/buysellsystemqueuefinal.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
        //update the system lot pool for html
    public static void updateRealTimeSystemLotPool() {        
        // Initialize Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("static/sortabletable/demo/");
        templateEngine.setTemplateResolver(templateResolver);
        // Prepare the template context and set the variable
        Context context = new Context();
        context.setVariable("lotPool", lotPool);
        // Process the template with the context
        String processedHtml = templateEngine.process("lootpooltable.html", context);

        try {
            // Write the processed HTML to the initial template HTML file
            String templatePath = "demo\\src\\main\\resources\\static\\sortabletable\\demo\\lootpooltablefinal.html";
            FileWriter writer = new FileWriter(templatePath);
            writer.write(processedHtml);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //open 500-lot pool places
    public static void openRealTime500lotpool() {
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:5500/demo/src/main/resources/static/sortabletable/demo/lootpooltablefinal.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static void RenderHTMLwebsite(){
        //update periodically the system queue html for 1 min
        Timer timer = new Timer();
        currentTask2 = new TimerTask() {
            @Override
            public void run() {
                updateRealTimeSystemQueue();
            }
        };
        timer.schedule(currentTask2, 0, 1* 60 * 1000);
        //update periodically the real tiem stock for 3 min
        Timer timer2 = new Timer();
        currentTask1 = new TimerTask() {
            @Override
            public void run() {
                StockDisplays.updateRealTimeStockTable();
            }
        };
        timer2.schedule(currentTask1, 0, 3 * 60 * 1000);
        //update periodically the real tiem system lot pool for 1 min
        Timer timer3 = new Timer();
        currentTask3 = new TimerTask() {
            @Override
            public void run() {
                updateRealTimeSystemLotPool();
            }
        };
        timer3.schedule(currentTask3, 0, 1 * 60 * 1000);
    }

    
}
