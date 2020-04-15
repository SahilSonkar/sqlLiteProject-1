package com.example.sqllite.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.CornerPathEffect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.contentcapture.ContentCaptureSession;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.example.sqllite.Activity.DetailsActivity;
import com.example.sqllite.Model.Grocery;
import com.example.sqllite.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";
    private Context
            context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = " CREATE TABLE " +
                Constants.TABLE_NAME + " (" +
                Constants.KEY_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_GROCERY_ITEM + " TEXT," +
                Constants.KEY_QTY_NUMBER + " TEXT," +
                Constants.KEy_DATE_NAME + " LONG);";
        db.execSQL(CREATE_GROCERY_TABLE);
        Toast.makeText(context, "Oncreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "onUpgrade", Toast.LENGTH_SHORT).show();
    }
    //CRUD OPERATION UPDATE,READ,DELETE CREATE METHODS

    //Add grocer
    public int addGrocery(Grocery grocery) {
        //take the permission to write
        SQLiteDatabase db = this.getWritableDatabase();
        //Initialise the container
        ContentValues contentValues = new ContentValues();
        //store the data, to be inserted in key-value pair
        contentValues.put(Constants.KEY_GROCERY_ITEM, grocery.getGroceryItem());
        contentValues.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        contentValues.put(Constants.KEy_DATE_NAME, java.lang.System.currentTimeMillis());
        //insert tha data into database
        int x= (int) db.insert(Constants.TABLE_NAME, null, contentValues);
        return x;
    }

    //get grocery
   public Grocery getGrocery(int id) {

        Grocery grocery = new Grocery();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_Id, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEy_DATE_NAME},
                Constants.KEY_Id + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {

            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_Id))));
            grocery.setGroceryItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEy_DATE_NAME))).getTime());
            grocery.setDateOfAddedeItem(formatedDate);
            grocery.setDateOfAddedeItem(formatedDate);
        }


        return grocery;
    }

    //get all grocery
    public List<Grocery> getAllGrocery() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_Id, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEy_DATE_NAME},
                null, null, null, null, Constants.KEy_DATE_NAME + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_Id))));
                grocery.setGroceryItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEy_DATE_NAME))).getTime());
                grocery.setDateOfAddedeItem(formatedDate);
                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }

        return groceryList;
    }

    // Update Grocery
    //return id tha wad updated ie,integer
    public int UpdateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.KEY_GROCERY_ITEM, grocery.getGroceryItem());
        contentValues.put(Constants.KEY_GROCERY_ITEM, grocery.getQuantity());
        contentValues.put(Constants.KEy_DATE_NAME,  java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME, contentValues,Constants.KEY_Id + "=?" ,new String[]{String.valueOf(grocery.getId())});
    }

    //delete grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //get Count
    public int getGroceryCount() {
        String countItem = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countItem, null);

        return cursor.getCount();
    }
}

