package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;

public class MyStockDetailActivity extends AppCompatActivity {

    public static final String TAG = MyStockDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stock_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String symbol  = intent.getStringExtra(MyStocksActivity.SYMBOL);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment myStockDetailFragment = MyStockDetailFragment.newInstance(symbol);
        fragmentTransaction.replace(R.id.fragment, myStockDetailFragment);

        fragmentTransaction.commit();

    }

}
