package de.kevintaron.popularmoviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.kevintaron.popularmoviesapp.data.FestMovieDetailsTask;
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
    @Bind(R.id.trailer_list) LinearLayout trailers;

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
        if(detail_movie == null) {
            detail_movie = getArguments().getParcelable("mymovie");
        }

        String[] values = { "Trailer 1", "Trailer 2", "Trailer 3"};

        for(int i = 0; i < values.length; i++) {
            TextView trailer = new TextView(new ContextThemeWrapper(getActivity(), R.style.TrailerStyle));
            trailer.setText(values[i]);
            trailers.addView(trailer);
        }


        if(detail_movie != null) {

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

            fetchMovieDetails();
        }



//        List<Movie> queryResults = new Select().from(Movie.class).orderBy("Name ASC").limit(10).queryList();
//        Log.i("DB", queryResults.get(1).getName());



        return rootView;
    }

    @OnClick(R.id.detail_movie_fav)
    public void onFavorite() {
        if(detail_movie != null) {
            sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if(editor != null) {

                if(isFavorite()) {
                    editor.remove(detail_movie.getMovieIdasString());
                    editor.commit();
                    fav.setImageDrawable(star_off);
                    detail_movie.delete();
                } else {
                    editor.putString(detail_movie.getMovieIdasString(), detail_movie.getName());
                    editor.commit();
                    fav.setImageDrawable(star_on);
                    detail_movie.save();
                }
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

    private void fetchMovieDetails() {
        FestMovieDetailsTask movieDetailsTask = new FestMovieDetailsTask("videos", detail_movie.getMovieIdasString());
        movieDetailsTask.execute();

        FestMovieDetailsTask movieDetailsTask2 = new FestMovieDetailsTask("reviews", detail_movie.getMovieIdasString());
        movieDetailsTask2.execute();
    }
}
