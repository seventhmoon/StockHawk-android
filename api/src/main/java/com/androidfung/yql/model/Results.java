package com.androidfung.yql.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fung on 3/13/2016.
 */
public class Results {
    @SerializedName("quote")
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }
}
