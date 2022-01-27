package com.example.recuperacionud2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recuperacionud2.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;
    TextView errorDisplay;

    ProgressBar requestProgress;

    public class superheroQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            requestProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String superHeroSearchResolve = null;

            try {
                superHeroSearchResolve = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return superHeroSearchResolve;
        }
        @Override
        protected void onPostExecute(String s) {
            requestProgress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showJsonData();
                searchResults.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.search) {
            URL githubUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
            urlDisplay.setText(githubUrl.toString());

            new superheroQueryTask().execute(githubUrl);

        }
        return true;
    }

    private void showJsonData() {
        errorDisplay.setVisibility(View.INVISIBLE);
        searchResults.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        searchResults.setVisibility(View.INVISIBLE);
        errorDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBox = (EditText)findViewById(R.id.search_box);
        urlDisplay = (TextView) findViewById(R.id.url_display);
        searchResults = (TextView) findViewById(R.id.sw_search_results);
        errorDisplay = (TextView) findViewById(R.id.error_display);

        requestProgress = (ProgressBar) findViewById(R.id.request_progress);
    }
}