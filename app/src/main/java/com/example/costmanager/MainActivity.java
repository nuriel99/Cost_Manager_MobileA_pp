package com.example.costmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.costmanager.Model.DatabaseHelper;
import com.example.costmanager.Model.Model;
import com.example.costmanager.ModelView.ViewModel;

/**
 * This is the main entry for our application.
 * our project is made by MVVM architecture
 * ,in this class we create each instance of MVVM
 * @author Maor Tene
 * @author Nuriel Mavashev
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creating the webview model
        View view = new View(new WebView(this));
        //creating the model
        Model model = new Model(new DatabaseHelper(this));
        //creating the ViewModel
        ViewModel viewModel = new ViewModel(view, model);
        //attaching viewmodel object to the web view
        view.getWebView().addJavascriptInterface(viewModel,"viewModel");
        //set the activity content
        setContentView(view.getWebView());
    }
}
