package com.sam_chordas.android.stockhawk.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidfung.yql.model.HistoricalData;
import com.androidfung.yql.model.Quote;
import com.androidfung.yql.service.ApiManager;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyStockDetailFragment extends Fragment {

    private static final String ARG_SYMBOL = "SYMBOL";
    private String mSymbol;

    public MyStockDetailFragment() {
    }

    public static MyStockDetailFragment newInstance(String symbol){
        MyStockDetailFragment f = new MyStockDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL, symbol);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSymbol = getArguments().getString(ARG_SYMBOL);

        }
    }


    private static final int DEFAULT_NUMBER_OF_DAYS = 7;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd", Locale.US);

    private com.db.chart.view.LineChartView mLineChart;
    private LineSet mDataSet;
    private DescriptiveStatistics mStats = new DescriptiveStatistics();

    private void loadData(String symbol) {
        ApiManager apiManager = new ApiManager(Volley.newRequestQueue(getActivity()));
        apiManager.getHistoricalData(mSymbol, DEFAULT_NUMBER_OF_DAYS, new Response.Listener<HistoricalData>() {
            @Override
            public void onResponse(HistoricalData historicalData) {
                mDataSet = new LineSet();
                mDataSet.setColor(Color.parseColor("#b3b5bb"))
                        .setFill(Color.parseColor("#2d374c"))
                        .setDotsColor(Color.parseColor("#ffc755"))
                        .setThickness(4);

                List<Quote> quotes = historicalData.getQuery().getResults().getQuotes();
                TreeMap<Date, Float> map = new TreeMap<>();
//                ArrayList<String> dateList = new ArrayList<String>();
//                ArrayList<Float> priceList = new ArrayList<Float>();
                for (Quote quote : quotes) {

                    float closingPrice = (float) quote.getClosingPrice();
                    map.put(quote.getDate(), closingPrice);


                }

                for (Date date : map.keySet()){
                    String dateLabel = DATE_FORMAT.format(date);
                    float closingPrice = map.get(date);
                    mStats.addValue(closingPrice);
//                    dateList.add(date);
//                    priceList.add(closingPrice);
                    mDataSet.addPoint(dateLabel, closingPrice);
                }

                int max = (int) Math.ceil(mStats.getMax());
                int min = (int) Math.floor(mStats.getMin());
                int gcd = ArithmeticUtils.gcd(max, min);

                mLineChart.setAxisBorderValues(min, max, gcd);
                mLineChart.addData(mDataSet);

                mLineChart.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("***", error.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_my_stock_detail, container, false);
        mLineChart = (LineChartView) rootView.findViewById(R.id.linechart);

        String symbol = getArguments().getString("Symbol");

        loadData(symbol);


        return rootView;
    }
}
