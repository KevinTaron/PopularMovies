package de.kevintaron.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * Movie Adapter - Adapter for Gridview in MoviePosterGridActivityFragment
 */
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

    public View getView(int position, View convertView, ViewGroup parent) {
        MovieHolder holder;

        if (convertView == null) {
            holder = new MovieHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_movieposter, null);
            holder.moviePoster = (ImageView) convertView.findViewById(R.id.grid_item_movieposter_imageView);
            convertView.setTag(holder);
        } else {
            holder = (MovieHolder) convertView.getTag();
        }


        final Movie movie = movies.get(position);
        String url = getContext().getString(R.string.api_image_base_url) + movie.getMoviePosterURL();

        Picasso.with(context).setIndicatorsEnabled(true);
        Picasso.with(context).load(url).into(holder.moviePoster);
        return convertView;
    }

    static class MovieHolder {
        ImageView moviePoster;
    }


}
