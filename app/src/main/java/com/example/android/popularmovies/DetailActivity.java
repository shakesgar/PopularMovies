package com.example.android.popularmovies;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.example.android.popularmovies.RVAdapter.VOTE_AVERAGE;

public class DetailActivity extends AppCompatActivity {
    private static final float BLUR_RADIUS = 25f;
    private static final String LOGTAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_detail);
        String backGroundImage = getIntent().getExtras().getString(RVAdapter.BACKGROUND);

        Target t = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d(LOGTAG, "BITMAP");
                Bitmap outputBitmap = Bitmap.createBitmap(bitmap);
                final RenderScript renderScript = RenderScript.create(DetailActivity.this);
                Allocation tmpIn = Allocation.createFromBitmap(renderScript, bitmap);
                Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

                //Intrinsic Gausian blur filter
                ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
                theIntrinsic.setRadius(BLUR_RADIUS);
                theIntrinsic.setInput(tmpIn);
                theIntrinsic.forEach(tmpOut);
                tmpOut.copyTo(outputBitmap);
                layout.setBackground(new BitmapDrawable(DetailActivity.this.getResources(), bitmap));

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w342/" + backGroundImage)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_holder)
                .into(t);
        layout.setTag(t);

        String movieTitle = getIntent().getExtras().getString(RVAdapter.TITLE);
        TextView title = (TextView) findViewById(R.id.titleDetail);
        title.setText(movieTitle);

        String movieOverview = getIntent().getExtras().getString(RVAdapter.OVERVIEW);
        TextView overview = (TextView) findViewById(R.id.overviewDetail);
        overview.setText(movieOverview);

        String movieReleaseDate = getIntent().getExtras().getString(RVAdapter.RELEASE_DATE);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDatDetail);
        String r = ("Released: " + movieReleaseDate);
        releaseDate.setText(r);

        String movieImage = getIntent().getExtras().getString(RVAdapter.POSTER_PATH);
        ImageView image = (ImageView) findViewById(R.id.imageDetail);
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w342/" + movieImage)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_holder)
                .into(image);

        Bundle movieRating = getIntent().getExtras();
        double rating = movieRating.getDouble(VOTE_AVERAGE);
        RatingBar mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingBar.setRating((float) rating);

        TextView textViewRating = (TextView) findViewById(R.id.ratingDetail);
        String s = ("Rating: " + String.valueOf(rating));
        textViewRating.setText(s);

    }
}
