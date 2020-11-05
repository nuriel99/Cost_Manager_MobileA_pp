package com.example.costmanager.ModelView;


import android.app.Activity;

import com.example.costmanager.Model.Model;
import com.example.costmanager.View;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ViewModel class handles the communication
 * between the WebView and the Model
 * It gets requests from the UI
 * and provides informations from the Model
 */
public class ViewModel extends Activity implements IViewModel {
    private View view;
    private Model model;
    private ExecutorService pool;
    private static final int MaxThreads = 5;

    /**
     * Constructor for ViewModel
     * @param view instance of view
     * @param model instance of model
     */
    public ViewModel(View view, Model model) {
        this.view = view;
        this.model = model;
        pool = Executors.newFixedThreadPool(MaxThreads);
    }


    /**
     * This method sends to the client all items
     * from the database for View Report page
     */
    @android.webkit.JavascriptInterface
    @Override
    public void getReport() {
        pool.submit(new Runnable() {
            public void run() {
                final JSONObject result = model.getItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.getWebView().evaluateJavascript("displayReport('"+result.toString()+"')", null);
                    }
                });
            }
        });
    }

    /**
     * This method adds new item to the database.
     * @param itemName item's name
     * @param category item's category
     * @param cost item's cost
     * @param date item's bought date
     */
    @android.webkit.JavascriptInterface
    @Override
    public void addItem(final String itemName,final String category,final String cost,final String date) {
        pool.submit(new Runnable() {
            public void run() {
                model.addData(itemName, category, cost, date);
                final JSONObject result = model.getItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.getWebView().evaluateJavascript("displayItemsList('" + result.toString() + "')", null);
                    }
                });
            }
        });
    }

    /**
     * This method sends to the client all items
     * from the database for Add Item page
     */
    @android.webkit.JavascriptInterface
    @Override
    public void showItems() {
        final JSONObject result = model.getItems();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.getWebView().evaluateJavascript("displayItemsList('"+result.toString()+"')", null);
            }
        });
    }

    /**
     * This method delete a item from the database
     * @param id id to delete a specific row from database
     */
    @android.webkit.JavascriptInterface
    @Override
    public void deleteItemVM(final String id) {
        pool.submit(new Runnable() {
            public void run() {
                model.deleteData(id);
                final JSONObject result = model.getItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.getWebView().evaluateJavascript("displayItemsList('"+result.toString()+"')", null);
                    }
                });
            }
        });
    }
}
