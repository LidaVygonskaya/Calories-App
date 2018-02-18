package com.example.lida.calories;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    public static final String LOG_TAG = "result";

    public String [] parseString(String string) {
        String delims = "[;]";
        String[] tokens = string.split(delims);
        return tokens;
    }

    public void showInf(TextView textView, String [] tokens) {
        int arraySize = tokens.length;
        for(int i = 0; i < arraySize; i++) {
            textView.append(tokens[i]);
            textView.append("\n");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String productName = intent.getStringExtra(MainActivity.EXTRA_PRODUCT);
        TextView textView = (TextView) findViewById(R.id.textView);
        showInf(textView, parseString(productName));




    }
}