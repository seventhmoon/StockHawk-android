package com.androidfung.yql.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.androidfung.yql.model.HistoricalData;
import com.androidfung.yql.network.GsonObjectRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by fung on 3/13/2016.
 */
public class ApiManager {


    private static final String TAG = ApiManager.class.getSimpleName();


    private static final String HOST = "https://query.yahooapis.com/v1/public/";
    private static final String HISTORICAL_QUERY = "yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22{symbol}%22%20and%20startDate%20%3D%20%22{startDate}%22%20and%20endDate%20%3D%20%22{endDate}%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    private RequestQueue mRequestQueue;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final long MILLISECONDS_IN_ONE_DAY =86400000;

    public ApiManager(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }


    public void getHistoricalData(String symbol, int numberOfDay, Response.Listener<HistoricalData> listener, Response.ErrorListener errorListener){
        Date today = new Date();
        Date startDate = new Date(today.getTime() - numberOfDay * MILLISECONDS_IN_ONE_DAY);
        Date endDate = new Date(today.getTime() -  MILLISECONDS_IN_ONE_DAY);
        GsonObjectRequest gsonReq = new GsonObjectRequest(Request.Method.GET, HOST + HISTORICAL_QUERY.replace("{symbol}",symbol).replace("{startDate}", DATE_FORMAT.format(startDate)).replace("{endDate}",DATE_FORMAT.format(endDate))
                , HistoricalData.class, null, listener, errorListener);
        mRequestQueue.add(gsonReq);
    }

//    private void getHistoricalData(String symbol, String startDate, String endDate, Response.Listener<HistoricalData> listener, Response.ErrorListener errorListener){
//        GsonObjectRequest gsonReq = new GsonObjectRequest(Request.Method.GET, HOST + HISTORICAL_QUERY.replace("{symbol}",symbol).replace("{startDate}", startDate).replace("{endDate}",endDate)
//                , HistoricalData.class, null, listener, errorListener);
//        mRequestQueue.add(gsonReq);
//    }

    private static String toUrlParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        for (String key : params.keySet()) {
            try {
                sb.append("&").append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().substring(1);
    }




}
