package com.example.demo;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class StockDisplays extends UpdateAndStoreLocal{
    private static final String breaker="█████████████████████████████████████████████████████████████████████████████████████████████████";
    public static TimerTask currentTask;

    //1 for as name,2 for ds name, 3 for as price, 4 for ds price, 5 for as symbol ,6 for ds symbol.
    public static void DisplayPreference(int n){
        String show="";
        String table="\n█"+String.format("%-32s","Stock Name")+"█"+String.format("%-31s","Symbol")+"█"+String.format("%-30s","Last Traded Price(MYR)")+"█\n█████████████████████████████████████████████████████████████████████████████████████████████████";
        show+="\n"+breaker+table;
        switch(n){
            case 1: 
                for(int i=0;i<stockDataNameBased.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataNameBased.get(i).getName())
                    +"█"+String.format("%-31s",stockDataNameBased.get(i).getCode())+
                    "█"+String.format("%-30s",stockDataNameBased.get(i).getLastDone())+"█";
                }
                break;
            case 2:
                for(int i=0;i<stockDataNameBasedReverse.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataNameBasedReverse.get(i).getName())+
                    "█"+String.format("%-31s",stockDataNameBasedReverse.get(i).getCode())+"█"+String.format("%-30s",stockDataNameBasedReverse.get(i).getLastDone())+"█";
                }
                break;
            case 3:
                for(int i=0;i<stockDataPriceBased.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataPriceBased.get(i).getName())+
                    "█"+String.format("%-31s",stockDataPriceBased.get(i).getCode())+"█"+String.format("%-30s",stockDataPriceBased.get(i).getLastDone())+"█";
                }   
                break;
            case 4:
                for(int i=0;i<stockDataPriceBasedReverse.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataPriceBasedReverse.get(i).getName())+
                    "█"+String.format("%-31s",stockDataPriceBasedReverse.get(i).getCode())+"█"+
                    String.format("%-30s",stockDataPriceBasedReverse.get(i).getLastDone())+"█";
                }
                break;
            case 5:
                for(int i=0;i<stockDataSymbolBased.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataSymbolBased.get(i).getName())+"█"+
                    String.format("%-31s",stockDataSymbolBased.get(i).getCode())+"█"+String.format("%-30s",stockDataSymbolBased.get(i).getLastDone())+"█";
                }
                break;
            case 6:
                for(int i=0;i<stockDataSymbolBasedReverse.size();i++){
                    show+="\n█"+String.format("%-32s",stockDataSymbolBasedReverse.get(i).getName())+"█"+
                    String.format("%-31s",stockDataSymbolBasedReverse.get(i).getCode())+"█"+
                    String.format("%-30s",stockDataSymbolBasedReverse.get(i).getLastDone())+"█";
                }
                break;
            default:
                break;
        }
        show+="\n"+breaker+"\n";
        System.out.print(show);
        System.out.println("Last stock data updated successfully at " + new java.util.Date());
        show="";
    }
    
    //-----------------------------------------------------------------------------------------
    //1 for as name,2 for ds name, 3 for as price, 4 for ds price, 5 for as symbol ,6 for ds symbol.

    public static void updateRealTimeStockTable() {
        // Initialize Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("static/sortabletable/demo/");
        templateEngine.setTemplateResolver(templateResolver);
        // Prepare the template context and set the variable
        Context context = new Context();
        context.setVariable("stockDataNameBased", stockDataNameBased);

        // Process the template with the context
        String processedHtml = templateEngine.process("html-data-table.html", context);

        try {
            // Write the processed HTML to the initial template HTML file
            String templatePath = "demo\\src\\main\\resources\\static\\sortabletable\\demo\\html-data-table-final.html";
            FileWriter writer = new FileWriter(templatePath);
            writer.write(processedHtml);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openRealTimeStockTableInBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:5500/demo/src/main/resources/static/sortabletable/demo/html-data-table-final.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}




    
    
