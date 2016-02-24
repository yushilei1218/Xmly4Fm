package com.yushilei.xmly4fm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView contentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        ImageView backImg = (ImageView) findViewById(R.id.web_back);
        contentTitle = (TextView) findViewById(R.id.web_title);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.web_progress);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.web_view);
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            WebViewClient client = new WebViewClient();

            webView.setWebViewClient(client);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {

                    if (newProgress < 100) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    contentTitle.setText(title);
                }
            });

            //通用设置
            WebSettings settings = webView.getSettings();

            //settings.setBuiltInZoomControls(true);
            //settings.setDefaultFontSize(30);
            settings.setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
