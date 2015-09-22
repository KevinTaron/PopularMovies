package de.kevintaron.popularmoviesapp;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }

}
