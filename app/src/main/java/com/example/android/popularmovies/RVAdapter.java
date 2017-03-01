package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

class RVAdapter extends RecyclerView.Adapter<RVAdapter.MovieViewHolder> {
    static final String TITLE = "title";
    static final String OVERVIEW = "overview";
    static final String RELEASE_DATE = "release_date";
    static final String POSTER_PATH = "poster_path";
    static final String BACKGROUND = "background";
    static final String VOTE_AVERAGE = "0.0";

    private Context context;
    private List<Movie> movies;

    RVAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        View mView;

        MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image);
            mView = itemView;

        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_layout, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int i) {
        final String image = movies.get(i).getPosterPath();
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w342/" + image)
                .into(holder.mImageView);
        holder.mImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Movie movie = movies.get(i);

                String image = movie.getPosterPath();
                String background = movie.getPosterBackdrop();
                String title = movie.getTitle();
                String overview = movie.getOverview();
                String releaseDate = movie.getReleaseDate();
                double rating = movie.getVoteAverage();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(POSTER_PATH, image);
                intent.putExtra(BACKGROUND, background);
                intent.putExtra(TITLE, title);
                intent.putExtra(OVERVIEW, overview);
                intent.putExtra(RELEASE_DATE, releaseDate);
                Bundle b = new Bundle();
                b.putDouble(VOTE_AVERAGE, rating);
                intent.putExtras(b);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.size();
    }
}
