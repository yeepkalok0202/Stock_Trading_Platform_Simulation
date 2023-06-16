package com.example.demo;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reporting {

    private static final String DEFAULT_DOWNLOAD_FOLDER = "Downloads";

    public static void downloadAsPdf(TradingUser user) {
        try {
            String downloadFolder = getDownloadFolderPath();
            String filePath = Paths.get(downloadFolder, "Report.pdf").toString();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            var headingFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            var userInfoFont = new Font(Font.FontFamily.HELVETICA, 12);
            var tableFont = new Font(Font.FontFamily.HELVETICA, 10);

            var paragraph = new Paragraph("Trading Report", headingFont);
            paragraph.add(new Phrase("\nUsername                        : " + user.getUsername(), userInfoFont));
            paragraph.add(new Phrase("\nBalance                         : " + user.getAccountBalance(), userInfoFont));
            paragraph.add(new Phrase("\nCumulative Profit and Loss (P&L): " + user.getcumulativeDifference(), userInfoFont));
            paragraph.add(new Phrase("\nCumulative P&L points           : " + user.getcumulativePLPoints(), userInfoFont));
            paragraph.add(new Phrase("\nTotal Sell Trade                : " + user.getTotalselltrade(), userInfoFont));
            paragraph.add(new Phrase("\nTotal Successful Sell Trade     : " + user.getcumulativeDifference(), userInfoFont));
            paragraph.add(new Phrase("\nWin Rate (%)                    : " + user.getWinrate()+" %", userInfoFont));

            var table = new PdfPTable(9);
            Stream.of("Time initiazed", "Time completed", "Activity", "Stock Name", "Number of shares", "Price per share",
                    "Total Cost", "P&L", "P&L Point").forEach(header -> {
                var cell = new PdfPCell(new Phrase(header, tableFont));
                table.addCell(cell);
            });

            for (TransactionStock history : user.tradeHistorys) {
                table.addCell(new Phrase(history.getTimeforactivity(), tableFont));
                table.addCell(new Phrase(history.getTimeforactivity2V(), tableFont));
                table.addCell(new Phrase(history.activity, tableFont));
                table.addCell(new Phrase(history.equitiesname, tableFont));
                table.addCell(new Phrase(String.valueOf(history.numberofshares), tableFont));
                table.addCell(new Phrase(String.valueOf(history.pricespershares), tableFont));
                table.addCell(new Phrase(String.valueOf(history.totalprices), tableFont));
                table.addCell(new Phrase(String.valueOf(history.getDifference()), tableFont));
                table.addCell(new Phrase(String.valueOf(history.getPLtrade()), tableFont));
            }
            paragraph.add(table);
            doc.add(paragraph);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void downloadAsCSV(TradingUser user) {
        try {
            String downloadFolder = getDownloadFolderPath();
            String filePath = Paths.get(downloadFolder, "Report.csv").toString();

            PrintWriter pw = new PrintWriter(new File(filePath));
            StringBuilder sb = new StringBuilder();

            // Append header
            sb.append("Time initialized, Time completed, Activity, Stock Name, Number of shares, Price per share,Total Cost, P&L, P&L Point\n");

            // Append trade history
            for (TransactionStock stock : user.tradeHistorys) {
                sb.append(stock.getTimeforactivity()).append(",")
                        .append(stock.getTimeforactivity2V()).append(",")
                        .append(stock.activity).append(",")
                        .append(stock.equitiesname).append(",")
                        .append(stock.numberofshares).append(",")
                        .append(stock.pricespershares).append(",")
                        .append(stock.totalprices).append(",")
                        .append(stock.getDifference()).append(",")
                        .append(stock.getPLtrade()).append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void downloadAsTXT(TradingUser user) {
        try {
            String downloadFolder = getDownloadFolderPath();
            String filePath = Paths.get(downloadFolder, "Report.txt").toString();
            PrintWriter pw = new PrintWriter(new File(filePath));
            StringBuilder sb = new StringBuilder();

            sb.append("User Report :\n\n\n");
            sb.append("\nUsername                        : ").append(user.getUsername());
            sb.append("\nBalance                         : ").append(user.getAccountBalance());
            sb.append("\nCumulative Profit and Loss (P&L): ").append(user.getcumulativeDifference());
            sb.append("\nCumulative P&L points           : ").append(user.getcumulativePLPoints());
            sb.append("\nTotal Sell Trade                : ").append(user.getTotalselltrade());
            sb.append("\nTotal Successful Sell Trade     : ").append(user.getcumulativeDifference());
            sb.append("\nWin Rate (%)                    : ").append(user.getWinrate()).append(" %");
        
            sb.append("\n\n\nTrade History: \n");
            sb.append("Time initialized, Time completed, Activity, Stock Name, Number of shares, Price per share,Total Cost, P&L, P&L Point\n\n");
            for (TransactionStock history : user.tradeHistorys) {
                sb.append(history.getTimeforactivity()+" ");
                sb.append(history.getTimeforactivity2V()+" ");
                sb.append(history.activity+" ");
                sb.append(history.equitiesname+" ");
                sb.append(String.valueOf(history.numberofshares)+" ");
                sb.append(String.valueOf(history.pricespershares)+" ");
                sb.append(String.valueOf(history.totalprices)+" ");
                sb.append(String.valueOf(history.getDifference()+" "));
                sb.append(String.valueOf(history.getPLtrade())+" \n");
            }
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDownloadFolderPath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, DEFAULT_DOWNLOAD_FOLDER).toString();
    }


}
