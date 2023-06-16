package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Portfolio implements Serializable{
    public transient PortfolioPriorityQueue<TransactionStock> heldStocks, listedStocks, buyList, tradeHistory;

    // open position, selling position, buying position and trade history (all are stored here)
    public ArrayList<TransactionStock> heldStockss, listedStockss, buyLists, tradeHistorys;
    

    //default comparator is naming
    public Portfolio() {
        heldStocks = new PortfolioPriorityQueue<>(checkforpreferences(1));
        listedStocks = new PortfolioPriorityQueue<>(checkforpreferences(1));
        buyList = new PortfolioPriorityQueue<>(checkforpreferences(1));
        tradeHistory = new PortfolioPriorityQueue<>(checkforpreferences(1));
        heldStockss= new ArrayList<>();
        listedStockss= new ArrayList<>();
        buyLists= new ArrayList<>();
        tradeHistorys= new ArrayList<>();
    }

    // method that choose preferences
    // number 1- for ascendingname, 2- for descending name, 3- ascending shares,
    // 4-descending shares, 5-ascending prices, 6- descending prices, 7 is ascending time,8 is descending time
    public Comparator<TransactionStock> checkforpreferences(int number) {
        Comparator<TransactionStock> nameAscendingComparator = Comparator.comparing(TransactionStock::getEquitiesname);
        Comparator<TransactionStock> nameDescendingComparator = nameAscendingComparator.reversed();
        Comparator<TransactionStock> sharesAscendingComparator = Comparator
            .comparingInt(TransactionStock::getNumberofshares);
        Comparator<TransactionStock> sharesDescendingComparator = sharesAscendingComparator.reversed();
        Comparator<TransactionStock> pricesAscendingComparator = Comparator
            .comparingDouble(TransactionStock::getPricespershares);
        Comparator<TransactionStock> pricesDescendingComparator = pricesAscendingComparator.reversed();
        Comparator<TransactionStock> dateAscendingComparator = Comparator.comparing(TransactionStock::getTimeforactivity);
        Comparator<TransactionStock> dateDescendingComparator = dateAscendingComparator.reversed();

        switch (number) {
            case 1:
                return nameAscendingComparator;
            case 2:
                return nameDescendingComparator;
            case 3:
                return sharesAscendingComparator;
            case 4:
                return sharesDescendingComparator;
            case 5:
                return pricesAscendingComparator;
            case 6:
                return pricesDescendingComparator;
            case 7:
                return dateAscendingComparator;
            case 8:
                return dateDescendingComparator; 
            default:
                return nameAscendingComparator; //if other than the input, return basic ascending name
        }
    }

    // number 1- for ascendingname, 2- for descending name, 3- ascending shares,
    // 4-descending shares, 5-ascending prices, 6- descending prices, 7 is ascending time,8 is descending time
    // Hold the stocks u have buy (not buying or selling, the price fluctate with
    // market)
    public String getHeldStocks(int number) {
        // Based on preferences customized the priority queue
        heldStocks = new PortfolioPriorityQueue<>(checkforpreferences(1));
        PortfolioPriorityQueue<TransactionStock> temp = new PortfolioPriorityQueue<TransactionStock>(
                checkforpreferences(number));
        // avoid duplication
        heldStocks.addAll(heldStockss);
        heldStockss.clear();
        while (!heldStocks.isEmpty()) {
            // poll out all the unsorted priority queue into a sorted priority queue
            // add into sorted priority queue
            temp.add(heldStocks.poll());
        }
        // temp contains sorted one
        while (!temp.isEmpty()) {
            // poll out all the sorted ones into the arraylist based on the received
            // arguement preferences
            TransactionStock temp2 = temp.poll();
            heldStockss.add(temp2);
            // add the sorted one into unsorted priority queue so that it retains all the
            // history of items of users.
            heldStocks.add(temp2);
        }
        // return the arraylist to string as the one in arraylist is sorted based on
        // preferences (in string)
        String y = "";
        String z ="";
        for (TransactionStock x : heldStockss) {
            z += x.toString();
        }
        for (TransactionStock x : heldStockss) {
            y+= x.MarketValue();
        }
        heldStocks.clear();
        return 
        "\nCurrent hold stocks:\n" + z +
                "\nCurrent hold stocks value:\n" + y;
    }

    // use new to create new object when buying in trading method
    public void addHeldStocks(TransactionStock a) {
       // a.activity = "Add hold stocks";
        //a.setTimeforactivity();
       // a.dateraw2=null;
        a.setTimeforactivity2V();  
    //    this.heldStocks.add(a);  this got bug pls pass it
        this.heldStockss.add(a);
        // add into trade history arraylist
       // this.tradeHistory.add(a);
    }

    // search for object reference, if got oni remove, else return null
    // add into trade history arraylist
    public void removeHeldStocks(TransactionStock a) {
      //  a.activity = "Remove hold stocks";
        a.setTimeforactivity();
       // a.setTimeforactivity2V();
    //    this.heldStocks.remove(a);   this got bug pls pass it
        this.heldStockss.remove(a);
      //  this.tradeHistory.add(a);
    }

    // number 1- for ascendingname, 2- for descending name, 3- ascending shares,
    // 4-descending shares, 5-ascending prices, 6- descending prices, 7 is ascending time,8 is descending time
    // List your current holding stocks to sell at specific price(should be equal or
    // slightly above the market price)
    public String getListedStocks(int number) {
        listedStocks = new PortfolioPriorityQueue<>(checkforpreferences(1));
        // Based on preferences customized the priority queue
        PortfolioPriorityQueue<TransactionStock> temp = new PortfolioPriorityQueue<TransactionStock>(
                checkforpreferences(number));
        // avoid duplication
        listedStocks.addAll(listedStockss);
        listedStockss.clear();
        while (!listedStocks.isEmpty()) {
            // poll out all the unsorted priority queue into a sorted priority queue
            // add into sorted priority queue
            temp.add(listedStocks.poll());
        }
        // temp contains sorted one
        while (!temp.isEmpty()) {
            // poll out all the sorted ones into the arraylist based on the received
            // arguement preferences
            TransactionStock temp2 = temp.poll();
            listedStockss.add(temp2);
            // add the sorted one into unsorted priority queue so that it retains all the
            // history of items of users.
            listedStocks.add(temp2);
        }
        listedStocks.clear();
        // return the arraylist to string as the one in arraylist is sorted based on
        // preferences (in string)
        return "\n"+listedStockss.toString();
    }


    // add into trade history arraylist also
    public void addListedStocks(TransactionStock a) {
    //    a.activity = "List sell";
        a.setTimeforactivity();
       // a.dateraw2=null;
        a.setTimeforactivity2V();
    //    this.listedStocks.add(a); this got bug pls pass it
        this.listedStockss.add(a);
     //   this.tradeHistory.add(a);
    }

    // add into trade history arraylist
    public void removeListedStocks(TransactionStock a) {
    //    a.activity = "Unlist sell";
        a.setTimeforactivity2V();
    //    this.listedStocks.remove(a); this got bug pls pass it
        this.listedStockss.remove(a);
      //  this.tradeHistory.add(a);
    }
    // number 1- for ascendingname, 2- for descending name, 3- ascending shares,
    // 4-descending shares, 5-ascending prices, 6- descending prices, 7 is ascending time,8 is descending time

    // List your current in pending buying list (most probably need to match the
    // seller price
    // normally equal to market price or/and below market price,
    // got chance where it is higher than market, if demand high)

    public String getBuyList(int number) {
        buyList = new PortfolioPriorityQueue<>(checkforpreferences(1));
        // Based on preferences customized the priority queue
        PortfolioPriorityQueue<TransactionStock> temp = new PortfolioPriorityQueue<TransactionStock>(
                checkforpreferences(number));
        // avoid duplication
        buyList.addAll(buyLists);
        buyLists.clear();
        while (!buyList.isEmpty()) {
            // poll out all the unsorted priority queue into a sorted priority queue
            // add into sorted priority queue
            temp.add(buyList.poll());
        }
        // temp contains sorted one
        while (!temp.isEmpty()) {
            // poll out all the sorted ones into the arraylist based on the received
            // arguement preferences
            TransactionStock temp2 = temp.poll();
            buyLists.add(temp2);
            // add the sorted one into unsorted priority queue so that it retains all the
            // history of items of users.
            buyList.add(temp2);
        }
        buyList.clear();
        // return the arraylist to string as the one in arraylist is sorted based on
        // preferences (in string)
        return "\n"+buyLists.toString();
    }

    // add into trade history arraylist
    public void addBuyList(TransactionStock a) {
   //     a.activity = "Process Buy";
        a.setTimeforactivity();
       // a.dateraw2=null;
        a.setTimeforactivity2V();
    //    this.buyList.add(a); this got bug pls pass it
        this.buyLists.add(a);
     //   this.tradeHistory.add(a);
    }

    public void removeBuyList(TransactionStock a) {
      //  a.activity = "Remove Buy";
        a.setTimeforactivity2V();
    //    this.buyList.remove(a);   this got bug pls pass it
        this.buyLists.remove(a);
      //  this.tradeHistory.add(a);
    }

    // number 1- for ascendingname, 2- for descending name, 3- ascending shares,
    // 4-descending shares, 5-ascending prices, 6- descending prices, 7 is ascending time,8 is descending time
    // List your current trade history (following preferences)

    public String getTradeHistory(int number) {
        tradeHistory = new PortfolioPriorityQueue<>(checkforpreferences(1));
        // let the transactionstock class to print activity type for each
        // Based on preferences customized the priority queue
        PortfolioPriorityQueue<TransactionStock> temp = new PortfolioPriorityQueue<TransactionStock>(
                checkforpreferences(number));
        // avoid duplication
        tradeHistory.addAll(tradeHistorys);
        tradeHistorys.clear();
        while (!tradeHistory.isEmpty()) {
            // poll out all the unsorted priority queue into a sorted priority queue
            // add into sorted priority queue
            temp.add(tradeHistory.poll());
        }
        // temp contains sorted one
        while (!temp.isEmpty()) {
            // poll out all the sorted ones into the arraylist based on the received
            // arguement preferences
            TransactionStock temp2 = temp.poll();
            tradeHistorys.add(temp2);
            // add the sorted one into unsorted priority queue so that it retains all the
            // history of items of users.
            tradeHistory.add(temp2);
        }
        // return the arraylist to string as the one in arraylist is sorted based on
        // preferences (in string)
        // added activity type
        String result="\n";
        for (int i = 0; i < tradeHistorys.size(); i++) {
            result+=("||" +(i+1)+"|| Activity: ||" +(tradeHistorys.get(i).activity.contains("Push")?tradeHistorys.get(i).getTimeforactivity():tradeHistorys.get(i).getTimeforactivity2V())+ "||\n||"+tradeHistorys.get(i).activity +"||\n" +tradeHistorys.get(i)+"\n");
        }
        tradeHistory.clear();
        return result;
    }

    
}
