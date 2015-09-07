package de.kevintaron.popularmoviesapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Get the Movie from Intent
        Movie detail_movie = getActivity().getIntent().getParcelableExtra("mymovie");
        Log.i("Detail", detail_movie.getName());

        // Load the Title
        TextView title = (TextView) rootView.findViewById(R.id.detail_movie_title_text);
        title.setText(detail_movie.getName());

        // Load Image to ImageView
        ImageView poster = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        String url = getActivity().getString(R.string.api_image_base_url) + detail_movie.getMoviePosterURL();
        Picasso.with(getActivity()).setIndicatorsEnabled(true);
        Picasso.with(getActivity()).load(url).into(poster);

        // Load the Summary
        TextView summary = (TextView) rootView.findViewById(R.id.detail_movie_summary);
        summary.setText(detail_movie.getSummary());

        // Rating
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.detail_movie_rating_bar);
        ratingBar.setRating(detail_movie.getVote_average());
        TextView ratingText = (TextView) rootView.findViewById(R.id.detail_movie_rating_text);
        ratingText.setText(detail_movie.getVote_average() + " / 10");

        // load the Release Date
        TextView release = (TextView) rootView.findViewById(R.id.detail_movie_release);
        String release_date = detail_movie.getRelease_date().split("-")[0];
        release.setText(release_date);


        return rootView;
    }
}
