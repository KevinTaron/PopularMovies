package de.kevintaron.popularmoviesapp;

import android.content.Intent;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import de.kevintaron.popularmoviesapp.data.FetchMoviesTask;
import de.kevintaron.popularmoviesapp.models.EndlessScrollListener;
import de.kevintaron.popularmoviesapp.models.Movie;

public class MoviePosterGridActivityFragment extends Fragment {

    private MovieAdapter mGridMovieposterAdapter;
    private FetchMoviesTask moviesTask;
    private String sortMethod = "popular";
    private GridView gridView;
    private boolean mTwoPane = false;

    public MoviePosterGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_poster_grid, container, false);

        List<Movie> mymovies = new ArrayList<>();

        mGridMovieposterAdapter = new MovieAdapter(getActivity(), R.layout.grid_item_movieposter, mymovies);

        gridView = (GridView) rootView.findViewById(R.id.gridview_movieposter);
        gridView.setAdapter(mGridMovieposterAdapter);

        if(rootView.findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

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
                updateMovies(page);
            }
        });

        return rootView;

    }

    private void updateMovies(int page) {
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
        updateMovies(1);
    }

}
