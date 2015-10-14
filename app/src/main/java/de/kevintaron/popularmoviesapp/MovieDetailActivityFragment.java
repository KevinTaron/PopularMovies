package de.kevintaron.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private ShareActionProvider mShareActionProvider;
    private String firstTrailer;

    public MovieDetailActivityFragment() {
        setHasOptionsMenu(true);
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

    public void addTrailer(String tailername, final String videoid) {
        final TextView trailer = new TextView(new ContextThemeWrapper(getActivity(), R.style.TrailerStyle));
        trailer.setText(tailername);

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoClient = new Intent(Intent.ACTION_VIEW);
                videoClient.setData(Uri.parse("http://m.youtube.com/watch?v=" + videoid));
                startActivityForResult(videoClient, 1234);
            }
        });

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                trailers.addView(trailer);
            }
        });
    }

    private void fetchMovieDetails() {
        FestMovieDetailsTask movieDetailsTask = new FestMovieDetailsTask(this, "videos", detail_movie.getMovieIdasString());
        movieDetailsTask.execute();

        FestMovieDetailsTask movieDetailsTask2 = new FestMovieDetailsTask(this, "reviews", detail_movie.getMovieIdasString());
        movieDetailsTask2.execute();

        if(mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_movie_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if(mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
    }


    private Intent createShareTrailerIntent() {
        Log.i("Test", "trailershare");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        String sharetext = "Check this cool trailer: " + this.firstTrailer;
        shareIntent.putExtra(Intent.EXTRA_TEXT, sharetext);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.menu_item_share) {
            share();
            return true;
        }

        return result;
    }

    public void setTrailer(String firstTrailer) {
        this.firstTrailer = "http://youtube.com/watch?v=" + firstTrailer;
    }

    public void share() {
        startActivity(createShareTrailerIntent());
    }


}
