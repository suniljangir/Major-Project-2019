package com.example.siddharth.grainprotection;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class sell extends AppCompatActivity {

    String url="http://www.enam.gov.in/NAM/home/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        WebView webView=(WebView)findViewById(R.id.webing);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setBackgroundColor(Color.TRANSPARENT);

        webView.loadUrl(url);
    }
}
