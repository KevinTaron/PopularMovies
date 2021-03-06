package de.kevintaron.popularmoviesapp.data;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.kevintaron.popularmoviesapp.MovieAdapter;
import de.kevintaron.popularmoviesapp.R;
import de.kevintaron.popularmoviesapp.models.Movie;

public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {
    private static final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private final String myapikey;
    private final MovieAdapter mGridMovieposterAdapter;
    private int page = 1;
    private final boolean addMovie;
    private final String sortMethod;
    private Activity myact;

    // These two need to be declared outside the try/catch
    // so that they can be closed in the finally block.
    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;

    public FetchMoviesTask(Activity myact, MovieAdapter gridMovieposterAdapter, int page, boolean addMovie, String sortMethod) {
        mGridMovieposterAdapter = gridMovieposterAdapter;
        myapikey = myact.getString(R.string.apikey);
        this.myact = myact;
        this.page = page;
        this.addMovie = addMovie;
        this.sortMethod = sortMethod;
    }

    @Override
    protected Movie[] doInBackground(String... params) {
        Log.i(LOG_TAG, "Start Sync");

        String sorting = getSortMethod();
        String movieJsonStr = null;
        Movie[] movieList;

        try {
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARAM = "sort_by";
            final String APIKEY_PARAM = "api_key";
            final String PAGE_PARAM = "page";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sorting)
                    .appendQueryParameter(APIKEY_PARAM, myapikey)
                    .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();

            Log.i(LOG_TAG, "JSON: " + movieJsonStr);
            movieList = getMoviesFromJson(movieJsonStr);
            return movieList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
        }

        return null;
    }

    private String getSortMethod() {
        String sorting = "popularity.desc";

        if(this.sortMethod.equals("rated")) {
            sorting = "vote_average.desc";
        }

        return sorting;
    }

    private Movie[] getMoviesFromJson(String movieJsonStr) throws JSONException {

        final String MOVIES_RESULT = "results";
        final String ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_POSTERURL = "poster_path";
        final String SUMMARY = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String MOVIE_RELEASEDATE = "release_date";


        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(MOVIES_RESULT);

        Movie[] movieList = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieObject = movieArray.getJSONObject(i);
            Movie nMovie = new Movie(movieObject.getInt(ID), movieObject.getString(MOVIE_TITLE),  movieObject.getString(MOVIE_POSTERURL), movieObject.getString(SUMMARY),
                    movieObject.getString(VOTE_AVERAGE), movieObject.getString(MOVIE_RELEASEDATE));

            movieList[i] = nMovie;

            Log.i(LOG_TAG, "Movie: " + nMovie.getName() + " " + nMovie.getMoviePosterURL() + " " + nMovie.getRelease_date() + " " + nMovie.getMovieId());
        }

        return movieList;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        if(movies != null && mGridMovieposterAdapter != null) {
            if(!addMovie || page == 1) {
                mGridMovieposterAdapter.clear();
            }

            for(Movie nMovie : movies) {
                mGridMovieposterAdapter.add(nMovie);
            }
        }
    }
}
