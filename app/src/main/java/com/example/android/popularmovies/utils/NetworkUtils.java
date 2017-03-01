package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3";
    private final static String API_KEY = "api_key";


    public static URL buildMoviesUrl(String movie) {
        Uri builtPosterPath = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .encodedQuery(movie)
                .build();

        URL url = null;
        try {
            url = new URL(builtPosterPath.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "Built URI " + url);

        return url;
    }

    public static String getData(String uri) {

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
