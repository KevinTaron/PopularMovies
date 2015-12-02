package de.kevintaron.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindString;
import de.kevintaron.popularmoviesapp.data.FetchMoviesTask;
import de.kevintaron.popularmoviesapp.models.EndlessScrollListener;
import de.kevintaron.popularmoviesapp.models.Movie;

public class MoviePosterGridActivityFragment extends Fragment {
    @BindString(R.string.pref_favorites)
    private String preference_file_key;
    private SharedPreferences sharedPref;

    private MovieAdapter mGridMovieposterAdapter;
    private FetchMoviesTask moviesTask;
    private String sortMethod = "popular";
    private boolean mTwoPane = false;

    public MoviePosterGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_poster_grid, container, false);

        List<Movie> mymovies = new ArrayList<>();

        mGridMovieposterAdapter = new MovieAdapter(getActivity(), R.layout.grid_item_movieposter, mymovies);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movieposter);
        gridView.setAdapter(mGridMovieposterAdapter);

        updateMovies(1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                if(mTwoPane) {
                    Bundle arguments = new Bundle();
//                    arguments.putString(MovieDetailActivityFragment.ARG_ITEM_ID, id);
                    arguments.putParcelable("mymovie", movie);
                    MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra("mymovie", movie);
                    startActivity(intent);
                }
            }
        });

        gridView.setOnScrollListener(new EndlessScrollListener(gridView) {

            @Override
            public void onLoadMore(int page) {
                if(!sortMethod.equals("fav")) { updateMovies(page); }
            }
        });

        return rootView;

    }

    private boolean checkIfOnline() {
        ConnectivityManager cm = (ConnectivityManager)this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void updateMovies(int page) {
        if(!checkIfOnline()) {
            Toast.makeText(this.getActivity(), R.string.offline_text, Toast.LENGTH_SHORT).show();
            updateSortMethod("fav");
            return;
        }

        if(moviesTask == null) {
            moviesTask = new FetchMoviesTask(this.getActivity(), mGridMovieposterAdapter, page, false, sortMethod);
            moviesTask.execute();
        } else {
            moviesTask = new FetchMoviesTask(this.getActivity(), mGridMovieposterAdapter, page, true, sortMethod);
            moviesTask.execute();
        }
    }

    public void updateSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
        if(sortMethod.equals("fav")) {
            showFavs();
        } else {
            updateMovies(1);
        }
    }

    private void showFavs() {
        mGridMovieposterAdapter.clear();
        sharedPref = getActivity().getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        Map<String,?> keys = sharedPref.getAll();

        Log.i("key", Integer.toString(keys.size()));

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.i("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
        }

        List<Movie> queryResults = new Select().from(Movie.class).orderBy("Name ASC").limit(10).queryList();

        for(int i  = 0; i < queryResults.size(); i++) {
            mGridMovieposterAdapter.add(queryResults.get(i));
        }
    }

    public void setSplitscreen(boolean split) {
        mTwoPane = split;
    }

}
