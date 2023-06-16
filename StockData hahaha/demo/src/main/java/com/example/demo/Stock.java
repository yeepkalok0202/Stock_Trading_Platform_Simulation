package com.example.demo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

public class Stock {
    @JsonProperty("NO")
    private String no;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("REM")
    private String remark;
    @JsonProperty("LAST DONE")
    private String lastDone;
    @JsonProperty("LACP")
    private String lacp;
    @JsonProperty("CHG")
    private String change;
    @JsonProperty("%CHG")
    private String percentageChange;
    @JsonProperty("VOL ('00)")
    private String volume;
    @JsonProperty("BUY VOL ('00)")
    private String buyVolume;
    @JsonProperty("BUY")
    private String buy;
    @JsonProperty("SELL")
    private String sell;
    @JsonProperty("SELL VOL")
    private String sellVolume;
    @JsonProperty("HIGH")
    private String high;
    @JsonProperty("LOW")
    private String low;
    @JsonProperty("CODE 2")
    private String code2;

    public Stock() {
        // Empty constructor needed for Jackson deserialization
    }

    public Stock(String no, String name, String code, String remark, String lastDone, String lacp, String change,
                String percentageChange, String volume, String buyVolume, String buy, String sell, String sellVolume,
                String high, String low, String code2) {
        this.no = no;
        this.name = name;
        this.code = code;
        this.remark = remark;
        this.lastDone = lastDone;
        this.lacp = lacp;
        this.change = change;
        this.percentageChange = percentageChange;
        this.volume = volume;
        this.buyVolume = buyVolume;
        this.buy = buy;
        this.sell = sell;
        this.sellVolume = sellVolume;
        this.high = high;
        this.low = low;
        this.code2 = code2;
    }
    public static Stock fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Stock.class);
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getLastDone() {
        return Double.parseDouble(lastDone);
    }

    public void setLastDone(String lastDone) {
        this.lastDone = lastDone;
    }

    public String getLacp() {
        return lacp;
    }

    public void setLacp(String lacp) {
        this.lacp = lacp;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(String percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(String buyVolume) {
        this.buyVolume = buyVolume;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getSellVolume() {
        return sellVolume;
    }

    public void setSellVolume(String sellVolume) {
        this.sellVolume = sellVolume;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String toString(){
        return "\nName: "+getName()+"\nCode:"+getCode()+"\nLatest Price:"+getLastDone()+"\n";
    }

    public String toAllDetails(){
        return "LACP: "+getLacp()+"\nCHG: "+getChange()+"\n%CHG: "+getPercentageChange()
        +"\nVOL('00): "+getVolume()+"\nBUY VOL('00): "+getBuyVolume()+
        "\nSELL VOL: "+getSellVolume()+"\nBUY: "+getBuy()+"\nSELL: "+getSell()
        +"\nHIGH: "+getHigh()+"\nLOW: "+getLow();
    }
    
}


