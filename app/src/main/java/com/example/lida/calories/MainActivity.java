package com.example.lida.calories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {
    //Для функции putExtra add extended data to intent
    public static final String EXTRA_PRODUCT = "com.example.myfirstapp.PRODUCT";
    public static final String EXTRA_INF = "com.example.myfirstapp.INF";
    final String LOG_TAG = "myLogs";;
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Log.d(LOG_TAG, "Hello!");

        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
        Log.d(LOG_TAG, "GET DBHELPER");

        String [] products = myDbHelper.getProductNames();

        ArrayAdapter<String> adapterProducts = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, products);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_product);
        textView.setAdapter(adapterProducts);

        //Dropdown menu
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.values_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.d(LOG_TAG, "spinner created");
        //Для базы данныхda

    }

    public void onMap (View view) {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    //Функция при нажатии на кнопку
    public void getResponse(View view) {

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_product);
        String product = textView.getText().toString();
        if (product.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Enter product";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
            Intent intent = new Intent(this, ResultActivity.class);
            Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
            TextView text = (TextView) findViewById(R.id.editText2);
            float mass = Float.valueOf(text.getText().toString());
            System.out.println(spinner.getSelectedItem().toString());

            c = myDbHelper.getInformation(product);
            Log.d(LOG_TAG, "GOT INFORMATION");

            System.out.println("SUCCE");
            if (c != null) {
                if (c.moveToFirst()) {
                    String str;
                    do {
                        str = "";
                        for (String cn : c.getColumnNames()) {
                            if (spinner.getSelectedItem().toString().compareTo("kg") == 0) {
                                str = str.concat(cn + " = "
                                        + c.getFloat(c.getColumnIndex(cn)) * mass * 10 + "; ");
                            } else {
                                str = str.concat(cn + " = "
                                        + c.getFloat(c.getColumnIndex(cn)) * mass / 100 + "; ");
                            }
                        }
                        Log.d(LOG_TAG, str);
                        intent.putExtra(EXTRA_PRODUCT, str);


                    } while (c.moveToNext());
                }
                c.close();
            } else
                Log.d(LOG_TAG, "Cursor is null");
            startActivity(intent);

        }
    }
}
