package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StockAPIResponse {
    
    @JsonProperty("result")
    private List<Stock> result;

    public List<Stock> getResult() {
        return result;
    }

    public void setResult(List<Stock> result) {
        this.result = result;
    }
}
