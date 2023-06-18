package com.example.demo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.text.DecimalFormat;

public class TransactionStock implements Serializable{
    public String equitiesname;
    public int numberofshares;
    public double pricespershares;
    public double totalprices;
    public String date;
    public String date2;
    public String activity;
    public LocalDateTime dateraw,dateraw2;
    public TradingUser user;
    public double difference;
    public double PLtrade;

    public double getPLtrade() {
        double value = this.PLtrade;

        // Format the value with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);

        // Parse the formatted value back to a double
        double roundedValue = Double.parseDouble(formattedValue);

        return roundedValue;
    }

    public void setPLtrade(double pLtrade) {
        PLtrade = pLtrade;
    }

    //for normal buying and selling
    public TransactionStock(String equitiesname, int numberofshares, double pricespershares) {
        this.equitiesname = equitiesname;
        this.numberofshares = numberofshares;
        this.pricespershares = pricespershares;
        this.totalprices = numberofshares*pricespershares;
        this.activity="";
        this.date=ClockFunction.getStringCurrentTime();
        this.dateraw=ClockFunction.getRawCurrentTime();
        this.date2="";
        this.dateraw2=null;
    }
    
    public double getDifference(){
        double value = this.difference;
        
        // Format the value with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);
        
        // Parse the formatted value back to a double
        double roundedValue = Double.parseDouble(formattedValue);
        
        return roundedValue;
    }
    public void setDifference(double difference){
        this.difference=difference;
    }
    public String getEquitiesname() {
        return equitiesname;
    }
    public void setEquitiesname(String equitiesname) {
        this.equitiesname = equitiesname;
    }
    public int getNumberofshares() {
        return numberofshares;
    }
    public void setNumberofshares(int numberofshares) {
        this.numberofshares = numberofshares;
    }
    public double getPricespershares() {
        return pricespershares;
    }
    public void setPricesperlot(double pricespershares) {
        this.pricespershares = pricespershares;
    }
    public double getTotalprices() {
        double value = this.totalprices;

        // Format the value with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);

        // Parse the formatted value back to a double
        double roundedValue = Double.parseDouble(formattedValue);

        return roundedValue;
    }

    public String getTotalpriceforhtml(){
        return String.format("%.2f",totalprices);
    }
    public String getActivityType(){
        return activity;
    }

    public void updatemarketprice(double a){
        this.pricespershares=a;
        this.totalprices=this.numberofshares*a;
    }
    public String toString(){
            return 
            "[Stock Name: "+equitiesname+
            " - Shares (in shares): "+numberofshares+
            " - Price /share: "+pricespershares+
            " - Total Amount: "+String.format("%.2f",numberofshares*pricespershares)+"]\n";
    }
    
    public double latestprice(String equitiesname){
        for(Stock x:UpdateAndStoreLocal.stockDataNameBased){
            if(equitiesname.equals(x.getName())){
                return x.getLastDone();
            }
        }
        return 0;
    }
    public String getlatestmarketprice(){
        return String.format("%.2f",latestprice(equitiesname));
    }
    public String MarketValue(){
        return 
        "[Stock Name: "+equitiesname+
        " - Shares (in shares): "+numberofshares+
        " - Price /share: "+latestprice(equitiesname)+
        " - Total Amount: "+String.format("%.2f",(latestprice(equitiesname))*numberofshares)+"]\n";
    }

    public String markettotalprice(){
        return String.format("%.2f",(latestprice(equitiesname))*numberofshares);
    }
    //set time of event
    public void setTimeforactivity(){
        this.date= ClockFunction.getStringCurrentTime();
        this.dateraw=ClockFunction.getRawCurrentTime();
    }
    public void setTimeforactivity2V(){
        this.date2= ClockFunction.getStringCurrentTime();
        this.dateraw2= ClockFunction.getRawCurrentTime();
    }
    public LocalDateTime getdateRAW(){
        return dateraw;
    }
    //return time of event
    public String getTimeforactivity(){
        return date;
    }
    public String getTimeforactivity2V(){
        return date2;
    }
    public String getdifferencebetweentime(){
        return ClockFunction.getDifferenceBetweenTwoTime(dateraw, dateraw2);
    }
    public void setUser(TradingUser user){
        this.user=user;
    }
    public TradingUser getUser() {
        return user;
    }
    public String toStringWithDateAndType() {
        return String.format("[Date: %s],[Type: %s], Order: %s", getTimeforactivity(),activity,toString());
    }
}
