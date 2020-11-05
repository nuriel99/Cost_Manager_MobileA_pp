package com.example.costmanager.Model;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class helps to handle the logic of our application
 * and communicate with our database and the ViewModel unit
 */
public class Model implements IModel {

    private DatabaseHelper database;

    /**
     * Constructor for Model
     * @param database our database
     */
    public Model(DatabaseHelper database) {
        this.database = database;
    }

    /**
     * This method adds new item to database
     * @param itemName item's name
     * @param category item's category
     * @param cost item's cost
     * @param date item's bought date
     */
    @Override
    public void addData(String itemName, String category, String cost, String date) {
        database.insertData(itemName, category, cost, date);
    }

    /**
     * This method gets all items from database
     * @return Returning JSONObject obejct which represents our data
     */
    @Override
    public JSONObject getItems() {
        JSONObject result = cursorToJson(database.getItems());
        return result;
    }

    /**
     * This method converts the Cursor object to a JSONObject
     * Because it's more easier in the client side to parse it with build-in functions
     * @param cursor Our Data from database represented by cursor instance
     * @return Our data from database represented by JSONObject instance
     */
    private JSONObject cursorToJson(Cursor cursor) {
        JSONObject jsonObject = new JSONObject();
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        try{
            jsonObject.put("myitems", resultSet);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        /*
         * scan columns for each row - represented by Cursor object
         * and construct new item - represented by JSONObject
         */
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return jsonObject;
    }

    /**
     * This method deletes a specifc row from database according to id
     * @param id id to delete a specific row from database
     */
    @Override
    public void deleteData(String id) {
        database.deleteDataDB(id);
    }
}
