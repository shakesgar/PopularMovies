package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO (1) Add your own API key below
    private static final String API_KEY = "";
    List<Movie> movieList = new ArrayList<>();
    ProgressBar mProgressBar;
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (isOnline()) {
            requestData("https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY);
        } else {
            Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_popular_movies) {
            if (isOnline()) {
                hideRecyclerView();
                requestData("https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY);

            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.show_toprated_movies) {
            if (isOnline()) {
                hideRecyclerView();
                requestData("https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY);

            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideRecyclerView() {
        rv.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void requestData(String uri) {
        showProgressBar();
        new FetchMyDataTask(this, new FetchMyDataTaskCompleteListener()).execute(uri);

    }

    protected void updateDisplay() {
        hideProgressBar();
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setVisibility(View.VISIBLE);

        GridLayoutManager llm = new GridLayoutManager(this, calculateNoOfColumns(getBaseContext()));

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter(this, movieList);
        rv.setAdapter(adapter);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<List> {

        @Override
        public void onTaskComplete(List result) {
            movieList = result;
            updateDisplay();
        }
    }
}
