package de.kevintaron.popularmoviesapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.kevintaron.popularmoviesapp.data.FetchMoviesTask;
import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviePosterGridActivityFragment extends Fragment {

    private MovieAdapter mGridMovieposterAdapter;

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

        updateMovies();

        return rootView;

    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask(this.getActivity(), mGridMovieposterAdapter);
        moviesTask.execute();
    }
}
