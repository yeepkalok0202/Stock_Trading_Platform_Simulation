package com.example.demo;

import java.util.Scanner;

public class TradingDashboard{
    
    public static String result;
    public static void DashBoard(TradingUser a){
        result="";
        result+=
        "=========================================================================================================================\n|"
        +String.format("%64s","|              MY TRADING DASHBOARD")+String.format("%57s","|\n")+
        "========================================================================================================================="
        +String.format("\n%-30s","|Account Balance  ")
        +String.format("|%-30s",a.getAccountBalance())
        +String.format("%60s","|")
        +String.format("\n%-30s","|Qualified for next day?  ")
        +String.format("|%-30s",a.checkQualificationForNextDay())
        +String.format("%60s","|")
        +String.format("\n%-30s","|Cumulative P&L  ")
        +String.format("|%-30s",a.getcumulativeDifference())
        +String.format("%60s","|")
        +String.format("\n%-30s","|Current Day P&L ")
        +String.format("|%-30s",a.getDifferenceForDay())
        +String.format("%60s","|")
        +String.format("\n%-30s","|Cumulative P&L points ")
        +String.format("|%-30s",a.getcumulativePLPoints())
        +String.format("%60s","|")
        +String.format("\n%-30s","|Current Day P&L points ")
        +String.format("|%-30s",a.getPlPointsofday())
        +String.format("%60s","|")
        +String.format("\n\n%-30s","|Open Positions (Ascending Name)")
        +String.format("%60s","=========================================================================================")
        +String.format("\n\n%-30s",a.getHeldStocks(1))
        +String.format("\n\n%-30s","|Trade History  (Ascending Name)")
        +String.format("%60s","=========================================================================================")
        +String.format("\n\n%-30s\n", a.getTradeHistory(1))
        +"=========================================================================================================================\n";
        System.out.println(result);
        //suppress the warning of the non closing resource errors
        @SuppressWarnings("resource")
        Scanner xx=new Scanner(System.in);
        System.out.println("Sort your current trade history (Key in the number following preferences)" +
        "\n1 Ascending Name \n2 Descending Name  \n3 Ascending Shares \n4 Descending Shares"+
        "\n5 Ascending Prices\n6 Descending Prices\n7 is Ascending Time\n8 is Descending Time ");
        System.out.print("\nPress enter to skip trade history sorting\n>");
        String input=xx.nextLine();
        if(input.isEmpty()){
            return;
        }
        while(Integer.parseInt(input)>8 ||Integer.parseInt(input)<1){
            System.out.println("Invalid input : Key in only acceptable range number");
            input=xx.nextLine();
            if(input.isEmpty()){
                return;
            }
        }
        System.out.print("\n\n\n|Trade History");
        switch(input){
            case("1"):System.out.println(" (Sorted by Ascending Name)   =========================================================================================");break;
            case("2"):System.out.println(" (Sorted by Descending Name)  =========================================================================================");break;
            case("3"):System.out.println(" (Sorted by Ascending Shares) =========================================================================================");break;
            case("4"):System.out.println(" (Sorted by Descending Shares)=========================================================================================");break;
            case("5"):System.out.println(" (Sorted by Ascending Prices) =========================================================================================");break;
            case("6"):System.out.println(" (Sorted by Descending Prices)=========================================================================================");break;
            case("7"):System.out.println(" (Sorted by Ascending Time)   =========================================================================================");break;
            case("8"):System.out.println(" (Sorted by Descending Time)  =========================================================================================");break;
            default:return;
        }
        System.out.println(a.getTradeHistory(Integer.parseInt(input)));
    }
}
