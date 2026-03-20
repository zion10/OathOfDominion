package com.oathofdominion.grandarena;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView      = findViewById(R.id.webview);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        progressBar  = findViewById(R.id.progress_bar);

        setupWebView();
        setupSwipeRefresh();
        webView.loadUrl("file:///android_asset/index.html");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings s = webView.getSettings();

        // Core JS + storage
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);

        // File access — needed to load assets and fetch CDN images
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);

        // Viewport / zoom
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);

        // Media + cache
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Custom user agent — lets the app identify itself to the API
        s.setUserAgentString(
            "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 " +
            "OathOfDominion/1.0 GrandArena/Android"
        );

        // JS Bridge
        webView.addJavascriptInterface(new Bridge(), "AndroidBridge");

        // WebViewClient — handle page events
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                // Signal to JS that we're in Android native mode
                view.evaluateJavascript(
                    "window.isAndroid=true;" +
                    "document.documentElement.setAttribute('data-platform','android');",
                    null
                );
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                String url = req.getUrl().toString();
                // External URLs open in browser
                if (!url.startsWith("file://") && !url.startsWith("about:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                return false;
            }
        });

        // Chrome client — loading progress
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setVisibility(progress < 100 ? View.VISIBLE : View.GONE);
                progressBar.setProgress(progress);
            }
            @Override
            public boolean onConsoleMessage(ConsoleMessage msg) {
                return true; // Suppress console noise
            }
        });

        // Hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setBackgroundColor(0xFF08090F); // Match --char color
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(0xFFFF6600, 0xFFFFAA00, 0xFFFFDD44);
        swipeRefresh.setProgressBackgroundColorSchemeColor(0xFF1A0A00);
        swipeRefresh.setOnRefreshListener(() -> webView.reload());
    }

    // ── JavaScript → Android Bridge ────────────────────────────────────
    public class Bridge {

        @JavascriptInterface
        public String getPlatform() { return "android"; }

        @JavascriptInterface
        public void showToast(String msg) {
            runOnUiThread(() ->
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show()
            );
        }

        @JavascriptInterface
        public void openUrl(String url) {
            runOnUiThread(() ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            );
        }

        @JavascriptInterface
        public void shareText(String title, String text) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, title);
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, "Share via"));
            });
        }

        @JavascriptInterface
        public String getApiKey() {
            return "ga_142f0d37df0d92a0b79939f7473e6108359b6962d0bfb4c0";
        }

        @JavascriptInterface
        public String getVersion() { return "1.0.0"; }
    }

    // ── Back button: navigate WebView history ──────────────────────────
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // ── Lifecycle ──────────────────────────────────────────────────────
    @Override protected void onResume()  { super.onResume();  webView.onResume(); }
    @Override protected void onPause()   { super.onPause();   webView.onPause(); }
    @Override protected void onDestroy() {
        if (webView != null) { webView.stopLoading(); webView.destroy(); }
        super.onDestroy();
    }
}
