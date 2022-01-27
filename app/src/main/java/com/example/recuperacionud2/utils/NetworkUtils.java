package com.example.recuperacionud2.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String SWAPI_BASE_URL = "http://swapi.dev/api/";
    final static String SHOW_ALL_PLANETS = "planets/";

    public static URL buildUrl(String swSearch) {
        Uri builder = null;
        if (!swSearch.isEmpty()) {
            builder = Uri.parse(SWAPI_BASE_URL).buildUpon()
                    .appendPath(swSearch)
                    .build();
        } else {
            builder = Uri.parse(SWAPI_BASE_URL).buildUpon()
                    .appendPath(SHOW_ALL_PLANETS)
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
            if (scan.hasNext()) {
                return scan.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
