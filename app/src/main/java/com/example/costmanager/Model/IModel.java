package com.example.costmanager.Model;

import org.json.JSONObject;

/**
 * Represents an interface of our database.
 * gives more accessibility and flexibility to the code
 */
public interface IModel {

    void addData(String itemName,String category,String cost,String date);
    public JSONObject getItems();
    public void deleteData(String id);
}