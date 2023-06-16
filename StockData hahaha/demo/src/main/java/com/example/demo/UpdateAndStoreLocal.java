package com.example.demo;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;



public class UpdateAndStoreLocal {

    private static final String API_URL = "http://localhost:3000/api/hello";
    private static final String JSON_FILE_PATH_NameAS = "stock_data_NameAS.json";
    private static final String JSON_FILE_PATH_PriceAS = "stock_data_PriceAS.json";
    private static final String JSON_FILE_PATH_SymbolAS = "stock_data_SymbolAS.json";
    private static final String JSON_FILE_PATH_NameDS = "stock_data_NameDS.json";
    private static final String JSON_FILE_PATH_priceDS = "stock_data_priceDS.json";
    private static final String JSON_FILE_PATH_SymbolDS = "stock_data_SymbolDS.json";

    private static final int UPDATE_INTERVAL_MINUTES = 3;

    protected static StockDataPriorityQueue<Stock> stockQueueNameBased = new StockDataPriorityQueue<>(Comparator.comparing(Stock::getName));
    protected static StockDataPriorityQueue<Stock> stockQueueSymbolBased = new StockDataPriorityQueue<>(Comparator.comparing(Stock::getCode));
    protected static StockDataPriorityQueue<Stock> stockQueuePriceBased = new StockDataPriorityQueue<>(Comparator.comparingDouble(Stock::getLastDone));
    protected static StockDataPriorityQueue<Stock> stockQueueNameBasedReverse = new StockDataPriorityQueue<>(Comparator.comparing(Stock::getName, Comparator.reverseOrder()));
    protected static StockDataPriorityQueue<Stock> stockQueueSymbolBasedReverse = new StockDataPriorityQueue<>(Comparator.comparing(Stock::getCode, Comparator.reverseOrder()));
    protected static StockDataPriorityQueue<Stock> stockQueuePriceBasedReverse = new StockDataPriorityQueue<>(Comparator.comparingDouble(Stock::getLastDone).reversed());
    
    public static ArrayList<Stock> stockDataNameBased=new ArrayList<>();
    public static ArrayList<Stock> stockDataSymbolBased=new ArrayList<>();
    public static ArrayList<Stock> stockDataPriceBased=new ArrayList<>();
    public static ArrayList<Stock> stockDataNameBasedReverse=new ArrayList<>();
    public static ArrayList<Stock> stockDataSymbolBasedReverse=new ArrayList<>();
    public static ArrayList<Stock> stockDataPriceBasedReverse=new ArrayList<>();


    // Initialize RestTemplate outside the scheduled method
    private RestTemplate restTemplate = new RestTemplate();

    public static void UpdateStock() {
        UpdateAndStoreLocal updateAndStoreLocal = new UpdateAndStoreLocal();
		System.out.println("\n["+ClockFunction.getStringCurrentTime()+"] Stock data updating....");
        updateAndStoreLocal.runPeriodically();
    }
    public void runPeriodically() {
        while (true) {
            try {
                fetchAndStoreStockData();        
                Thread.sleep(UPDATE_INTERVAL_MINUTES * 60 * 1000); //every 3 minutes interval
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fetchAndStoreStockData() {
        try {   
                String docs="";
                // Create the request body as a JSON object
                String requestBody = "{\"docs\": " + docs + "}";
                StockAPIResponse response = restTemplate.postForObject(API_URL, requestBody, StockAPIResponse.class);
                if (response != null && response.getResult() != null) {
                    for (Stock stock : response.getResult()) {
                        if (!shouldIgnoreStock(stock)) {
                            stockQueueNameBased.add(stock);
                            stockQueuePriceBased.add(stock);
                            stockQueueSymbolBased.add(stock);
                            stockQueueNameBasedReverse.add(stock);
                            stockQueuePriceBasedReverse.add(stock);
                            stockQueueSymbolBasedReverse.add(stock);
                        }
                    }
            }
            
            storeStockDataToJsonFile();
        } catch (Exception e) {
            // Handle API connection issues gracefully
            System.err.println("An error occurred while fetching or storing stock data: " + e.getMessage());
            System.out.println("Stock data not syncning at " + ClockFunction.getStringCurrentTime()+"\nPlease consult the API server.");
        }
    }

    private  boolean shouldIgnoreStock(Stock stock) {
        // Add the condition to check if the stock contains the specific content you want to ignore such as irrelevant website data
        return stock.getName().equals("Subscribe") ||
            stock.getCode().equals("Investor Relations\n\nNewsroom") ||
            stock.getRemark().equals("Subscribe");
    }

    private  void storeStockDataToJsonFile() {
        //clear the arraylist first in case of anything inside
        stockDataNameBased.clear();
        stockDataSymbolBased.clear();
        stockDataPriceBased.clear();
        stockDataNameBasedReverse.clear();
        stockDataSymbolBasedReverse.clear();
        stockDataPriceBasedReverse.clear();
        try (
            FileWriter fileWriter_NameAS = new FileWriter(JSON_FILE_PATH_NameAS);
            FileWriter fileWriter_PriceAS = new FileWriter(JSON_FILE_PATH_PriceAS);
            FileWriter fileWriter_SymbolAS = new FileWriter(JSON_FILE_PATH_SymbolAS);
            FileWriter fileWriter_NameDS = new FileWriter(JSON_FILE_PATH_NameDS);
            FileWriter fileWriter_priceDS = new FileWriter(JSON_FILE_PATH_priceDS);
            FileWriter fileWriter_SymbolDS = new FileWriter(JSON_FILE_PATH_SymbolDS);
            FileWriter fileWriter_FullData = new FileWriter("StockFullData.json"); )
            {
            while(!stockQueueNameBased.isEmpty()){
                Stock stockNameBased= stockQueueNameBased.poll();
                Stock stockPriceBased = stockQueuePriceBased.poll();
                Stock stockSymbolBased = stockQueueSymbolBased.poll();
                Stock stockNameBasedReverse = stockQueueNameBasedReverse.poll();
                Stock stockPriceBasedReverse = stockQueuePriceBasedReverse.poll();
                Stock stockSymbolBasedReverse = stockQueueSymbolBasedReverse.poll();
                //store into arraylist
                stockDataNameBased.add(stockNameBased);
                stockDataSymbolBased.add(stockSymbolBased);
                stockDataPriceBased.add(stockPriceBased);
                stockDataNameBasedReverse.add(stockNameBasedReverse);
                stockDataSymbolBasedReverse.add(stockSymbolBasedReverse);
                stockDataPriceBasedReverse.add(stockPriceBasedReverse);

                String json_NameAS = "{\"CODE\": \"" + stockNameBased.getCode() + "\",\"NAME\": \"" + stockNameBased.getName() +
                        "\", \"LAST DONE\": \"" + stockNameBased.getLastDone() + "\"}\n";
                String json_PriceAS = "{\"CODE\": \"" + stockPriceBased.getCode() + "\",\"NAME\": \"" + stockPriceBased.getName() +
                        "\", \"LAST DONE\": \"" + stockPriceBased.getLastDone() + "\"}\n";
                String json_SymbolAS = "{\"CODE\": \"" + stockSymbolBased.getCode() + "\",\"NAME\": \"" + stockSymbolBased.getName() +
                        "\", \"LAST DONE\": \"" + stockSymbolBased.getLastDone() + "\"}\n";
                String json_NameDS = "{\"CODE\": \"" + stockNameBasedReverse.getCode() + "\",\"NAME\": \"" + stockNameBasedReverse.getName() +
                        "\", \"LAST DONE\": \"" + stockNameBasedReverse.getLastDone() + "\"}\n";  
                String json_priceDS = "{\"CODE\": \"" + stockPriceBasedReverse.getCode() + "\",\"NAME\": \"" + stockPriceBasedReverse.getName() +
                        "\", \"LAST DONE\": \"" + stockPriceBasedReverse.getLastDone() + "\"}\n";       
                String json_SymbolDS = "{\"CODE\": \"" + stockSymbolBasedReverse.getCode() + "\",\"NAME\": \"" + stockSymbolBasedReverse.getName() +
                        "\", \"LAST DONE\": \"" + stockSymbolBasedReverse.getLastDone() + "\"}\n";   
                String json_FullData = "{\"CODE\": \"" + stockSymbolBasedReverse.getCode() + "\",\"NAME\": \"" + stockSymbolBasedReverse.getName() +
                        "\", \"LAST DONE\": \"" + stockSymbolBasedReverse.getLastDone() + "\",\"LACP\": \"" + stockSymbolBasedReverse.getLacp() + 
                        "\",\"CHG\": \"" + stockSymbolBasedReverse.getChange() +
                        "\", \"%CHG\": \"" + stockSymbolBasedReverse.getPercentageChange()+ "\",\"VOL ('00)\": \"" + stockSymbolBasedReverse.getVolume() +
                        "\",\"BUY VOL ('00)\": \"" + stockSymbolBasedReverse.getBuyVolume() +
                        "\", \"SELL VOL\": \"" + stockSymbolBasedReverse.getSellVolume() + "\",\"HIGH\": \"" + stockSymbolBasedReverse.getHigh() +
                        "\", \"LOW\": \"" + stockSymbolBasedReverse.getLow() + "\",\"BUY\": \"" + stockSymbolBasedReverse.getBuy() + 
                        "\",\"SELL\": \"" + stockSymbolBasedReverse.getSell() +"\"}\n";        
                
                fileWriter_NameAS.write(json_NameAS);
                fileWriter_PriceAS.write(json_PriceAS);
                fileWriter_SymbolAS.write(json_SymbolAS);
                fileWriter_NameDS.write(json_NameDS);
                fileWriter_priceDS.write(json_priceDS);
                fileWriter_SymbolDS.write(json_SymbolDS);
                fileWriter_FullData.write(json_FullData);

            }
        } catch (IOException e) {
            System.err.println("Error occurred while storing stock data: " + e.getMessage());
        }
    }
}