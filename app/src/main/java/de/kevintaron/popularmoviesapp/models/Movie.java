package de.kevintaron.popularmoviesapp.models;

/**
 * Created by Kevin on 28.08.2015.
 */
public class Movie {

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

}
