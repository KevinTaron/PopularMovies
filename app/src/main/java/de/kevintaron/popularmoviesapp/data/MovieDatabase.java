package de.kevintaron.popularmoviesapp.data;


import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;

@ContentProvider(authority = MovieDatabase.AUTHORITY,
        databaseName = MovieDatabase.NAME,
        baseContentUri = MovieDatabase.BASE_CONTENT_URI)
@Database(name = MovieDatabase.NAME, version = MovieDatabase.VERSION)
public class MovieDatabase {

    public static final String NAME = "Movies";

    public static final int VERSION = 1;

    public static final String AUTHORITY = "de.kevintaron.popularmovies.movie.provider";

    public static final String BASE_CONTENT_URI = "content://";

}
