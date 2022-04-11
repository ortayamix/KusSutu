package com.example.tefeul;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

public class WebviewAct extends AppCompatActivity {

    ActionBar actionBar;
    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    MyCustomWenViewClient myCustomWenViewClient;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_left_24);
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // HIDE back button on bar


        String link = getIntent().getStringExtra("link");
        webView = findViewById(R.id.web_view);
        /*progressBar = findViewById(R.id.progress_circular);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        progressDialog = new ProgressDialog(this);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            webView.reload();
        });*/

        //myCustomWenViewClient = new MyCustomWenViewClient();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(myCustomWenViewClient);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(link);
        //webView.loadUrl("https://www.refrefdergisi.com");
        //webView.loadUrl("https://www.google.com");

    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }


    //
    public class MyCustomWenViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println("webweb shouldOverrideUrlLoading");
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            System.out.println("webweb onPageStarted");
            if (!progressDialog.isShowing())
                progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            System.out.println("webweb onPageFinished");
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            //super.onReceivedError(view, request, error);
            System.out.println("webweb onReceivedError");
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            System.out.println("webweb onReceivedSslError");
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            message += "\"SSL Certificate Error\" Do you want to continue anyway?.. YES";
            Toast.makeText(WebviewAct.this, message, Toast.LENGTH_SHORT).show();

            handler.proceed();
        }
    }
}