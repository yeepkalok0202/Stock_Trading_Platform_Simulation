package com.example.demo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HistoricalPerformance {

    public static void openStockCodeURL(int stockCode) {
        String url = "https://www.malaysiastock.biz/Stock-Chart.aspx?securitycode=" + stockCode + "&mode=1D";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
