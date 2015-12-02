package de.kevintaron.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MoviePosterGridActivity extends AppCompatActivity {
    private boolean splitscreen;
    private FragmentManager fm;
    private MoviePosterGridActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_poster_grid);
        fm = getSupportFragmentManager();
        fragment = (MoviePosterGridActivityFragment)fm.findFragmentById(R.id.movie_grid_fragment);

        if(findViewById(R.id.movie_detail_container) != null) {
            Log.i("Splitscreen", "true");
            splitscreen = true;
        } else {
            Log.i("Splitscreen", "false");
            splitscreen = false;
        }
        if(fragment != null) {
            fragment.setSplitscreen(splitscreen);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_poster_grid, menu);
        return true;
    }

    public boolean isSplitscreen() {
        return this.splitscreen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_popular) {
            Log.i("Action", "sort by pop");
            fragment.updateSortMethod("popular");
            return true;
        }
        if (id == R.id.action_sort_rated) {
            Log.i("Action", "sort by rated");
            fragment.updateSortMethod("rated");
            return true;
        }
        if (id == R.id.action_sort_fav) {
            Log.i("Action", "sort by fav");
            fragment.updateSortMethod("fav");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
