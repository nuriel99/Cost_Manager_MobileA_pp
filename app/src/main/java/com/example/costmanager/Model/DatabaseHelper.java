package com.example.costmanager.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper class for executing SQL requests
 * and managing a local database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Costmanager.db";
    private static final String ITEM_TABLE = "PERSON_TABLE";
    private static final int DATABASE_VERSION = 1;
    private static final String COL_2 = "ITEM_NAME";
    private static final String COL_3 = "ITEM_CATEGORY";
    private static final String COL_4 = "ITEM_COST";
    private static final String COL_5 = "ITEM_DATE";

    /**
     * Constructor for database
     * @param context database's context
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method create a custom table inside the database.
     * @param db database's instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ITEM_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT)";
        db.execSQL(createTableStatement);
    }

    /**
     * This method is called when version of my database has changed.
     * @param db my database.
     * @param oldVersion version before the upgrade.
     * @param newVersion version after the upgrade.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE);
        onCreate(db);
    }

    /**
     * This method insert an item into the database.
     * @param itemName item's name.
     * @param category item's category.
     * @param cost item's cost.
     * @param date item's bought date.
     */
    public void insertData(String itemName, String category ,String cost ,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, itemName);
        cv.put(COL_3, category);
        cv.put(COL_4, cost);
        cv.put(COL_5, date);
        db.insert(ITEM_TABLE, null, cv);
    }

    /**
     * This method gets items from the table.
     * @return returning Cursor value when calling query.
     */
    public Cursor getItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+ITEM_TABLE, null);
        return result;
    }

    /**
     * This method for deleting an item.
     * @param id id to delete a specific row from database.
     */
    public void deleteDataDB(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEM_TABLE, "ID = ?", new String[] {id});
    }
}
