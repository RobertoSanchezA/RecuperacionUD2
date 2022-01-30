package com.example.recuperacionud2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recuperacionud2.entities.PlanetEntity;
import com.example.recuperacionud2.recyclerView.RepoAdapter;
import com.example.recuperacionud2.utils.NetworkUtils;
import com.example.recuperacionud2.utils.PlanetsJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RepoAdapter.ListItemClickListener{

    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;
    TextView errorDisplay;
    ProgressBar requestProgress;

    RecyclerView repoList;
    RepoAdapter adapter;

    Toast clickToast;
    @Override
    public void onListItemClick(int clickedItemIndex) {
        String toastMessage = "Se ha pulsado sobre " + clickedItemIndex;
        if (clickToast != null ){
            clickToast.cancel();
        }
        clickToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        clickToast.show();
    }
    public class SWQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            requestProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String SWSearchResolve = null;

            try {
                SWSearchResolve = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SWSearchResolve;
        }

        @Override
        protected void onPostExecute(String s) {
            requestProgress.setVisibility(View.INVISIBLE);
            Log.i("error", "texto " + s);
            if (s != null) {
                try {
                    PlanetEntity[] parsedApiOutput = PlanetsJsonUtils.parseJson(s);
                    adapter.setRepoData(parsedApiOutput);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showJsonData();
                //searchResults.setText(s);
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

            URL swUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
            urlDisplay.setText(swUrl.toString());

            Log.e("a", swUrl.toString());
            new SWQueryTask().execute(swUrl);

        }
        if (itemId == R.id.clear) {
            urlDisplay.setText("");
            errorDisplay.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    private void showJsonData() {
        //errorDisplay.setVisibility(View.INVISIBLE);
        searchResults.setVisibility(View.VISIBLE);
        Log.e("resultado", searchResults.toString());
    }
    private void showErrorMessage() {
        //searchResults.setVisibility(View.INVISIBLE);
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

        repoList = (RecyclerView) findViewById(R.id.rv_responses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        requestProgress = (ProgressBar) findViewById(R.id.request_progress);

        repoList.setLayoutManager(layoutManager);
        repoList.setHasFixedSize(true);

        adapter = new RepoAdapter(this);
        repoList.setAdapter(adapter);
    }
}