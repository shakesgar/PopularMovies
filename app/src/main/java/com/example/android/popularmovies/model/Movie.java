package com.example.android.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String posterPath;
    private String posterBackdrop;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private Double voteAverage;

    private Movie(Parcel in) {
        posterPath = in.readString();
        posterBackdrop = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {

    }

    public String getPosterBackdrop() {
        return posterBackdrop;
    }

    public void setPosterBackdrop(String posterBackdrop) {
        this.posterBackdrop = posterBackdrop;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return originalTitle;
    }

    public void setTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(posterBackdrop);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(originalTitle);
    }
}
