package de.kevintaron.popularmoviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    @Bind(R.id.detail_movie_title_text) TextView title;
    @Bind(R.id.detail_movie_poster) ImageView poster;
    @Bind(R.id.detail_movie_summary) TextView summary;
    @Bind(R.id.detail_movie_rating_bar) RatingBar ratingBar;
    @Bind(R.id.detail_movie_rating_text) TextView ratingText;
    @Bind(R.id.detail_movie_release) TextView release;
    @Bind(R.id.detail_movie_fav) ImageView fav;

    @BindDrawable(android.R.drawable.star_off) Drawable star_off;
    @BindDrawable(android.R.drawable.star_on) Drawable star_on;

    @BindString(R.string.pref_favorites) String preference_file_key;

    Movie detail_movie;
    Context context;
    SharedPreferences sharedPref;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        context = getActivity();

        // Get the Movie from Intent
        detail_movie = getActivity().getIntent().getParcelableExtra("mymovie");

        sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);

        // Load the Title
        title.setText(detail_movie.getName());

        // Load Image to ImageView
        String url = getActivity().getString(R.string.api_image_base_url) + detail_movie.getMoviePosterURL();
        Picasso.with(getActivity()).setIndicatorsEnabled(true);
        Picasso.with(getActivity()).load(url).into(poster);

        // Load the Summary
        summary.setText(detail_movie.getSummary());

        // Rating
        ratingBar.setRating(detail_movie.getVote_average());
        ratingText.setText(ratingText.getText().toString() + " " + detail_movie.getVote_average() + " / 10");

        // load the Release Date
        String[] release_date = detail_movie.getRelease_date().split("-");
        release.setText(release_date[0]);

        // fav
        if(isFavorite()) {
            fav.setImageDrawable(star_on);
        }

//        List<Movie> queryResults = new Select().from(Movie.class).orderBy("Name ASC").limit(10).execute();
//        Log.i("DB", queryResults.get(0).getName());


        return rootView;
    }

    @OnClick(R.id.detail_movie_fav)
    public void onFavorite() {
        if(detail_movie != null) {
            sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if(isFavorite()) {
                editor.remove(detail_movie.getMovieIdasString());
                editor.commit();
                fav.setImageDrawable(star_off);
            } else {
                editor.putString(detail_movie.getMovieIdasString(), detail_movie.getName());
                editor.commit();
                fav.setImageDrawable(star_on);
            }
        }
    }

    private boolean isFavorite() {
        if(sharedPref.getString(detail_movie.getMovieIdasString(), null) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
