package com.example.android.popularmovies.parsers;

import com.example.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJSONParser {


    public static List<Movie> parseFeed(String content) {

        try {

            JSONObject object = new JSONObject(content);

            JSONArray moviesArray = object.getJSONArray("results");
            List<Movie> movieList = new ArrayList<>();


            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject obj = moviesArray.getJSONObject(i);
                Movie movie = new Movie();

                movie.setPosterPath(obj.getString("poster_path"));
                movie.setPosterBackdrop(obj.getString("backdrop_path"));
                movie.setOverview(obj.getString("overview"));
                movie.setReleaseDate(obj.getString("release_date"));
                movie.setTitle(obj.getString("title"));
                movie.setVoteAverage(obj.getDouble("vote_average"));

                movieList.add(movie);

            }

            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
