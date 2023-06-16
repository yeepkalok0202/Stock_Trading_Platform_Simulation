package com.example.demo;

import java.util.PriorityQueue;
import java.util.Comparator;

public class PortfolioPriorityQueue<T> extends PriorityQueue<T>{

    public PortfolioPriorityQueue(){
      
    }

    public PortfolioPriorityQueue(Comparator<? super T> comparator) {
        super(comparator);
    }
}
