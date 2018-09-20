package io.duckduckgosearch.app;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements SearchTask.OnTaskFinish {

    EditText searchBar;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        fragmentManager = getSupportFragmentManager();

        context = this;

        progressBar = findViewById(R.id.search_progress);

        searchBar = findViewById(R.id.search_bar_edittext);
        searchBar.requestFocus();
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                progressBar.setVisibility(View.VISIBLE);
                WebViewFragment webViewFragment = WebViewFragment.newInstance(v.getText().toString());
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, webViewFragment)
                        .commit();
                return false;
            }
        });
    }

    @Override
    public void onTaskFinish(String finalHtmlCode) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Log.i("HTML", finalHtmlCode);
        progressBar.setVisibility(View.GONE);
        WebViewFragment webViewFragment = WebViewFragment.newInstance(finalHtmlCode);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, webViewFragment)
                .commit();
    }
}
