package io.duckduckgosearch.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewFragment extends android.support.v4.app.Fragment {

    private static final String SEARCH_URL = "https://duckduckgo.com/?q=";
    String data = "";
    WebView webView;

    public WebViewFragment() {
    }

    public static WebViewFragment newInstance(@NonNull String data) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        if (getArguments() != null) {
            data = getArguments().getString("data");
        }
        webView = view.findViewById(R.id.search_web_view);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        CookieManager.getInstance().setCookie("duckduckgo.com", "o=-1");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl(
                        "javascript:$(\".header--aside\").remove(); $(\"#header_wrapper\").css(\"padding-top\", \"0\")"
                );
                webView.setVisibility(View.VISIBLE);
                webView.requestFocus();
            }
        });
        webView.loadUrl(SEARCH_URL + data);
        return view;
    }

}
