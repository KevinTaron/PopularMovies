package de.kevintaron.popularmoviesapp.models;

import android.widget.AbsListView;
import android.widget.GridView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    private final GridView gridView;
    private boolean isLoading;
    private int pageNumber = 1;

    public EndlessScrollListener(GridView gridView) {
        this.gridView = gridView;
        this.isLoading = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (gridView.getLastVisiblePosition() + 1 == (totalItemCount) && !isLoading) {
            isLoading = true;
            pageNumber++;
            int maxPages = 1000;
            if(pageNumber < maxPages) {
                onLoadMore(pageNumber);
            }

        } else {
            isLoading = false;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

}