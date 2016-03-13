package com.androidfung.yql.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by fung on 3/13/2016.
 */
public class Quote implements Comparable<Quote>{
    /*
         "Symbol": "YHOO",
     "Date": "2010-03-10",
     "Open": "16.51",
     "High": "16.940001",
     "Low": "16.51",
     "Close": "16.790001",
     "Volume": "33088600",
     "Adj_Close": "16.790001"
     */
    @SerializedName("Symbol")
    private String symbol;
    @SerializedName("Date")
    private Date date;
    @SerializedName("Close")
    private double closingPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    @Override
    public int compareTo(Quote another) {
        if (this.symbol == another.getSymbol()){
            return this.getDate().compareTo(another.getDate());
        }else{
            return this.symbol.compareTo(another.getSymbol());
        }

    }
}
