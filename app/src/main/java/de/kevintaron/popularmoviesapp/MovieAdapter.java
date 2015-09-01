package de.kevintaron.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.kevintaron.popularmoviesapp.models.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {
    Context context;
    int layoutResourceId;
    List<Movie> movies = null;

    public MovieAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
        this.layoutResourceId = resource;
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        MovieHolder holder = null;

        if(item == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);

            holder = new MovieHolder();
            holder.moviePoster = (ImageView)item.findViewById(R.id.grid_item_movieposter_imageView);

            item.setTag(holder);
            Movie movie = movies.get(position);
            String url = getContext().getString(R.string.api_image_base_url) + movie.getMoviePosterURL();

            Log.i("Test", "mymovie: " + url);

            Picasso.with(context).load(url).into(holder.moviePoster);
        } else {
            holder = (MovieHolder)item.getTag();
        }

        return item;
    }

    static class MovieHolder {
        ImageView moviePoster;
    }


}
