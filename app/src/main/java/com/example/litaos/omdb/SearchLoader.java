package com.example.litaos.omdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

public class SearchLoader extends AsyncTaskLoader<SearchService.MovieList> {
    private static final String TAG = "SearchLoader";

    private String queryTitle;
    private SearchService.MovieList searchResult;

    public SearchLoader(@NonNull Context context, String queryTitle) {
        super(context);
        this.queryTitle = queryTitle;
    }

    @Nullable
    @Override
    public SearchService.MovieList loadInBackground() {
        try {
            SearchService.MovieList movieList = SearchService.searchMovieList(queryTitle);
            Log.i(TAG, "loadInBackground movieList size: " + movieList.movieList.size());
            return movieList;
        } catch(IOException e) {
            Log.e(TAG, "loadInBackground error for query OMDB API " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if(searchResult != null) {
            deliverResult(searchResult);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        searchResult = null;
    }

    @Override
    public void deliverResult(@Nullable SearchService.MovieList data) {
        if(isReset()) return;
        searchResult = data;
        if(isStarted()) {
            super.deliverResult(data);
        }
    }
}
