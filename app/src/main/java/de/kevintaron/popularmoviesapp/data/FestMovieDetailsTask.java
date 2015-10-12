package de.kevintaron.popularmoviesapp.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.kevintaron.popularmoviesapp.MovieDetailActivityFragment;
import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * Created by Kevin on 21.09.2015.
 */
public class FestMovieDetailsTask extends AsyncTask<String, Void, Void> {
    public static final String LOG_TAG = FestMovieDetailsTask.class.getSimpleName();

    private String method;
    private String myapikey;
    private String movieID;
    private MovieDetailActivityFragment detailFragment;

    // These two need to be declared outside the try/catch
    // so that they can be closed in the finally block.
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;


    public FestMovieDetailsTask(MovieDetailActivityFragment thefragment, String method, String movieID) {
        this.detailFragment = thefragment;
        this.method = method;
        myapikey = "fce9e5f238e7933224d9bcb4f1b5d201";
        this.movieID = movieID;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.i(LOG_TAG, "Start Sync");

        String movieJsonStr = null;
        Movie[] movieList = null;

        try {
            String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
            MOVIE_BASE_URL += this.movieID + "/";
            MOVIE_BASE_URL += this.method;

            final String SORT_PARAM = "sort_by";
            final String APIKEY_PARAM = "api_key";
            final String PAGE_PARAM = "page";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(APIKEY_PARAM, myapikey)
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

            if(method == "videos") {
                getVideosFromJson(movieJsonStr);
            }

//            movieList = getMoviesFromJson(movieJsonStr);
//            return movieList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
        }

        return null;
    }

    protected void onPostExecute() {

    }

    private void getReviewsFromJson() {

    }

    private void getVideosFromJson(String json) throws JSONException {
        final String MOVIES_RESULT = "results";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";


        JSONObject movieJson = new JSONObject(json);
        JSONArray movieArray = movieJson.getJSONArray(MOVIES_RESULT);

        Movie[] movieList = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieObject = movieArray.getJSONObject(i);
            detailFragment.addTrailer(movieObject.getString(NAME) + " (" + movieObject.getString(SITE) + ")", movieObject.getString(KEY));
        }

    }
}
