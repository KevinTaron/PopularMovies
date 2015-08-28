package de.kevintaron.popularmoviesapp.data;

import de.kevintaron.popularmoviesapp.models.Movie;

/**
 * Created by Kevin on 28.08.2015.
 */
public class MovieProvider {
    Movie[] movielist;

    public MovieProvider() {
        Movie batman = new Movie("Batman");
        Movie spiderman = new Movie("Spiderman");
        Movie saw = new Movie("Saw");
        Movie stromberg = new Movie("Stromberg der Film");
        Movie xmen = new Movie("X-Men");
        Movie xmen2 = new Movie("X-Men 2");
        Movie internship = new Movie("The Internship");
        Movie superdaddy = new Movie("Super Daddy");
        Movie hdr = new Movie("Herr der Ringe");
        Movie bb = new Movie("Bad Boys");
        Movie shrek = new Movie("Shrek");

        movielist = new Movie[] {
                batman, spiderman, saw, stromberg, xmen, xmen2, internship, superdaddy, hdr, bb, shrek
        };
    }

    public Movie[] getMovies() {
        return movielist;
    }
}
