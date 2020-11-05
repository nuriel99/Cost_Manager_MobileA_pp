package com.example.costmanager;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * View class contains only Webview component.
 * for displaying our UI, using only web technologies.
 */
public class View {

    private WebView webView;

    /**
     * Constructor for View
     * @param webView instance of webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    public View(WebView webView) {
        this.webView = webView;
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl("file:///android_asset/index.html");
    }

    /**
     * Getter for retrieving WebView component
     * @return WebView Component, which represents UserInterface.
     */
    public WebView getWebView() {
        return webView;
    }
}