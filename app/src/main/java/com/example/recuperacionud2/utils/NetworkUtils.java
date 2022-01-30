package com.example.recuperacionud2.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    //https://swapi.dev/api/planets/1/?format=wookiee
    final static String SWAPI_BASE_URL = "http://swapi.dev/api/planets";
    final static String SORT = "format";
    final static String SORT_BY = "json";

    public static URL buildUrl(String swSearch) {
        Uri builder = null;
        Log.e("juan", swSearch);
        if (!swSearch.isEmpty()) {
            builder = Uri.parse(SWAPI_BASE_URL).buildUpon()
                    .appendEncodedPath(swSearch + "/")
                    .appendQueryParameter(SORT, SORT_BY)
                    .build();
            
        }
        if(swSearch.isEmpty()) {
            builder = Uri.parse(SWAPI_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT, SORT_BY)
                    .build();
            }

        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream is = urlConnection.getInputStream();
        Scanner scan = new Scanner(is);
        scan.useDelimiter("\\A");
        try {
            boolean hasInput = scan.hasNext();
            if(hasInput) return scan.next();
            else return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
