package de.kevintaron.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 28.08.2015.
 */
public class Movie implements Parcelable {

    private int id;
    private String name;
    private String moviePosterURL;
    private String summary;
    private int vote_average;
    private String release_date;

    public Movie(String name) {
        this.name = name;
    }
    public Movie(int id, String name, String moviePosterURL, String summary, int vote_average, String release_date) {
        setId(id);
        setName(name);
        setMoviePosterURL(moviePosterURL);
        setSummary(summary);
        setVote_average(vote_average);
        setRelease_date(release_date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }


    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        moviePosterURL = in.readString();
        summary = in.readString();
        vote_average = in.readInt();
        release_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(moviePosterURL);
        dest.writeString(summary);
        dest.writeInt(vote_average);
        dest.writeString(release_date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}