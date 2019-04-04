package com.example.litaos.omdb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {
    public static String KEY_MOVIE = "key_movie";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_view);
        Movie movie = getIntent().getParcelableExtra(KEY_MOVIE);
        TextView movieTitle = findViewById(R.id.movie_detail_title);
        movieTitle.setText(movie.title);
    }
}
