package com.example.demo;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StockDataPriorityQueue<T> extends PriorityQueue<T> {

    public StockDataPriorityQueue(Comparator<? super T> comparator) {
        super(comparator);
    }

}
