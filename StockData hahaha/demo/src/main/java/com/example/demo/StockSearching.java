package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Search the stock live time when the API update
public class StockSearching {
    private static List<Stock> stocks;

    public StockSearching() {
        stocks = new ArrayList<>();
    }

    public void loadStocksFromJSON(String filePath) throws IOException {
        stocks.removeAll(stocks);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Stock stock = Stock.fromJson(line);
                stocks.add(stock);
            }
        }
    }

    public List<Stock> searchStocks(String pattern) {
        List<Stock> results = new ArrayList<>();

        for (Stock stock : stocks) {
            if (search(stock.getName(), pattern) || search(stock.getCode(), pattern)) {
                results.add(stock);
            }
        }

        return results;
    }

    private boolean search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        int[] last = new int[256];
        for (int i = 0; i < 256; i++) {
            last[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            last[pattern.charAt(i)] = i;
        }

        int i = m - 1;
        int j = m - 1;

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                if (j == 0) {
                    return true; // pattern found
                }
                i--;
                j--;
            } else {
                i += m - Math.min(j, 1 + last[text.charAt(i)]);
                j = m - 1;
            }
        }

        return false; // pattern not found
    }

    // final method to call in this class
    public static void StockSearch() {
        try {
            StockSearching stockSearch = new StockSearching();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------   Search box  -------------------------------------------------------------");
            stockSearch.loadStocksFromJSON("StockFullData.json"); // Replace with the actual file path
            // take input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("----------------------------------------------  Enter a search pattern (or 'exit' to quit)  ---------------------------------------------\n ");
                String input = br.readLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                List<Stock> searchResults = stockSearch.searchStocks(input.toUpperCase());
                if (searchResults.isEmpty()) {
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("---------------------------------------------No stocks matching the search pattern found-------------------------------------------------\n");
                } else {
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("Stocks matching the search pattern:");
                    for (Stock stock : searchResults) {
                        System.out.println(stock);
                    }
                    //stop the annoying warning
                    @SuppressWarnings("resource")
                    Scanner xx=new Scanner(System.in);
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("---------------------------------------------------Want full information on the equities?---------------------------------------------------\n1-Yes\n2-No\n>");
                    String line=xx.nextLine();
                    if(line.contains("1")){
                        for (Stock stock : searchResults) {
                            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println(stock.getName());
                            System.out.println(stock.toAllDetails()+"\n");
                            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

                        }
                    }
                }
                stocks.removeAll(searchResults);
                stockSearch.loadStocksFromJSON("StockFullData.json"); // Replace with the actual file path

            }
        } catch (IOException e) {
            System.out.println(
                    "Error occurred while loading stocks: " + e.getMessage() + "\nPlease reload the stock update :>");
        }
    }
}
