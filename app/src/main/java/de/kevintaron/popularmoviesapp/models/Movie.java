package de.kevintaron.popularmoviesapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;
import com.raizlabs.android.dbflow.structure.provider.BaseProviderModel;
import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import de.kevintaron.popularmoviesapp.data.MovieDatabase;

/**
 * Movie Model to create a Movie for the Grid and DetailView
 * Created by Kevin on 28.08.2015.
 */
@TableEndpoint(name = Movie.NAME, contentProviderName = "MovieDatabase")
@Table(databaseName = MovieDatabase.NAME)
public class Movie extends BaseProviderModel<Movie> implements Parcelable {

    private static final String NAME = "Movie";

    @ContentUri(path = NAME, type = ContentUri.ContentType.VND_MULTIPLE + NAME)
    private static final Uri CONTENT_URI = ContentUtils.buildUri(MovieDatabase.AUTHORITY, new String[]{NAME});


    @Column
    @PrimaryKey
    private int id;
    @Column
    private String name;
    @Column
    private String moviePosterURL;
    @Column
    private String summary;
    @Column
    private float vote_average;
    @Column
    private String release_date;

    public Movie() { super(); }

    /**
     * Movie Constructor
     * @param name set the Moviename
     */
    public Movie(String name) {
        this.name = name;
    }

    /**
     * Movie Constructor
     * @param id set the Movie ID
     * @param name set the Moviename
     * @param moviePosterURL set the URL to the movieposter
     * @param summary set the summary of the movie
     * @param vote_average set the vote average
     * @param release_date set the release date of the movie
     */
    public Movie(int id, String name, String moviePosterURL, String summary, String vote_average, String release_date) {
        setMovieId(id);
        setName(name);
        setMoviePosterURL(moviePosterURL);
        setSummary(summary);
        setVote_average(Float.valueOf(vote_average));
        setRelease_date(release_date);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    /**
     * Get the ID
     * @return the id
     */
    public int getMovieId() {
        return id;
    }

    public String getMovieIdasString() { return Integer.toString(id); }

    /**
     * Set the ID
     * @param id set the ID
     */
    private void setMovieId(int id) {
        this.id = id;
    }

    /**
     * Get the Release Date of the Movie
     * @return the release Date in YYYY-MM-DD
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * Set the Release Date of the Movie
     * @param release_date set the release Date in YYYY-MM-DD
     */
    private void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * Get the Name of the Movie
     * @return the Moviename
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name of the Movie
     * @param name the Moviename
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Get the Movieposter URL
     * @return movieposter URL
     */
    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    /**
     * Set the Movieposter URL
     * @param moviePosterURL set the URL
     */
    private void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }

    /**
     * Get the Movie Summary
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set the Summary
     * @param summary the moviesummary
     */
    private void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Get the Vote Average of the movie
     * @return the vote average out of / 10
     */
    public float getVote_average() {
        return vote_average;
    }

    /**
     * Set the Vote average
     * @param vote_average out of / 10
     */
    private void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public Uri getDeleteUri() {
        return Movie.CONTENT_URI;
    }

    @Override
    public Uri getInsertUri() {
        return Movie.CONTENT_URI;
    }

    @Override
    public Uri getUpdateUri() {
        return Movie.CONTENT_URI;
    }

    @Override
    public Uri getQueryUri() {
        return Movie.CONTENT_URI;
    }

    /**
     * Create Parcelable Movie
     * @param in informations
     */
    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        moviePosterURL = in.readString();
        summary = in.readString();
        vote_average = in.readFloat();
        release_date = in.readString();
    }

    /**
     * Not used
     * @return nothing
     */
    public int describeContents() {
        return 0;
    }

    /**
     * Write the Parcel Informations
     * @param dest destination
     * @param flags flags (not used)
     */
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(moviePosterURL);
        dest.writeString(summary);
        dest.writeFloat(vote_average);
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