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

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviePosterGridActivityFragment extends Fragment {

    ArrayAdapter<String> mGridMovieposterAdapter;

    public MoviePosterGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_poster_grid, container, false);

        String[] testArray = {
                "Batman",
                "Spiderman",
                "Terminator",
                "Saw",
                "X-Men",
                "Bla",
                "Etc"
        };

        List<String> testItems = new ArrayList<String>(Arrays.asList(testArray));
        mGridMovieposterAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.grid_item_movieposter,
                R.id.grid_item_movieposter_textView,
                testItems
        );

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movieposter);
        gridView.setAdapter(mGridMovieposterAdapter);

        return rootView;

    }
}
