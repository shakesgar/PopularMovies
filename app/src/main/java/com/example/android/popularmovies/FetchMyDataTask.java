package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.parsers.MovieJSONParser;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;


class FetchMyDataTask extends AsyncTask<String, String, String> {

    private Context context;
    private AsyncTaskCompleteListener listener;


    FetchMyDataTask(Context ctx, AsyncTaskCompleteListener listener) {
        this.context = ctx;
        this.listener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

     /*The type of data passed in matches the first parameter in the Async declaration<String, ...
     The type of the return matches the last param declared , String>*/
    @Override
    protected String doInBackground(String... params) {
        return NetworkUtils.getData(params[0]);

    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        List<Movie> movieList = MovieJSONParser.parseFeed(result);
        if (movieList != null) {
            listener.onTaskComplete(movieList);

        } else {
            Toast.makeText(context, "Something Happened, Please try again!", Toast.LENGTH_SHORT).show();
        }

    }
}