package com.example.lida.calories;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by lida on 18.07.17.
 */

public class DataBaseHelper extends SQLiteAssetHelper {
    private static String DB_NAME = "Calories.db";
    private final String LOG_TAG = "dbLog";
    private SQLiteDatabase myDataBase;
    private static final String[] COLUMNS = {"proteins", "fats", "carbohydrates", "kkals"};
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, 1);
        Log.d(LOG_TAG, "hello from database helper");
    }


   public Cursor getInformation(String productName) {
       SQLiteDatabase db = getReadableDatabase();
       SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
       String sqlTables = "Calories";
       qb.setTables(sqlTables);
       String selection = "_id" + "=" + "'" + productName + "'";
       Cursor c = qb.query(db,COLUMNS, selection, null,null,null,null);
       c.moveToFirst();
       return c;
   }

   public String[] getProductNames() {
       SQLiteDatabase db = getReadableDatabase();
       SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
       String sqlTables = "Calories";
       String[] columns = new String[] {"_id"};
       qb.setTables(sqlTables);
       Cursor c = qb.query(db, columns, null, null, null, null, null);
       c.moveToFirst();
       ArrayList<String> productNames = new ArrayList<String>();
       while (!c.isAfterLast()) {
           productNames.add(c.getString(c.getColumnIndex("_id")));
           c.moveToNext();
       }
       c.close();
       return productNames.toArray(new String[productNames.size()]);
   }
}
