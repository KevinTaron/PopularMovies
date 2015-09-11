package de.kevintaron.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Movie Model to create a Movie for the Grid and DetailView
 * Created by Kevin on 28.08.2015.
 */
@Table(name="Movies")
public class Movie extends Model implements Parcelable {

    @Column(name="movie_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "poster_url")
    private String moviePosterURL;
    @Column(name = "summary")
    private String summary;
    @Column(name = "vote_average")
    private float vote_average;
    @Column(name = "release_date")
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
    public void setMovieId(int id) {
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
    public void setRelease_date(String release_date) {
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
    public void setName(String name) {
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
    public void setMoviePosterURL(String moviePosterURL) {
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
    public void setSummary(String summary) {
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
    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
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