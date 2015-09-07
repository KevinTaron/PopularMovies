package de.kevintaron.popularmoviesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.kevintaron.popularmoviesapp.data.FetchMoviesTask;
import de.kevintaron.popularmoviesapp.models.EndlessScrollListener;
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

        updateMovies(1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                Log.i("test", movie.getName());
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("mymovie", movie);
                startActivity(intent);
            }
        });

//        gridView.setOnScrollListener(new EndlessScrollListener() {
          //  @Override
  //          public void onLoadMore(int page, int totalItemsCount) {
    //            Log.d("Gridview", "Load more Items");
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
      //          updateMovies(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
        //    }
        //});

        // Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        // startActivity(intent);


        return rootView;

    }

    private void updateMovies(int page) {
        FetchMoviesTask moviesTask = new FetchMoviesTask(this.getActivity(), mGridMovieposterAdapter, page);
        moviesTask.execute();
    }
}
