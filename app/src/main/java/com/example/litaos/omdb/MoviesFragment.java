package com.example.litaos.omdb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoviesFragment extends Fragment {
    private static final String KEY_PAGE = "PAGE";
    private static final String KEY_TITLE = "TITLE";
    private String title;
    private int page;
    private List<Movie> movieList = new ArrayList<>();

    public static MoviesFragment newInstance(int page, String title) {
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE, page);
        bundle.putString(KEY_TITLE, title);
        moviesFragment.setArguments(bundle);
        return moviesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(KEY_PAGE, 0);
        title = getArguments().getString(KEY_TITLE,  getString(R.string.all_fragment));
    }

    private void initMoviewlist() {
        for(int i = 0; i < 6; i++) {
            Movie movie = new Movie("Movie Title: " + i + "\n" + randomPlot(), i);
            movieList.add(movie);
        }
    }

    private String randomPlot() {
        Random rand = new Random();
        int randn = rand.nextInt(10);
        StringBuilder sb = new StringBuilder();
        sb.append("Plot: ");
        for(int i = 0; i < randn; i++) {
            sb.append("justsomerandomtext");
        }
        return sb.toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
        initMoviewlist();
        RecyclerView recyclerView = view.findViewById(R.id.movie_recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        MovieAdapter movieAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);
        TextView titleTextView = view.findViewById(R.id.fragment_title);
        titleTextView.setText(title);
        return view;
    }
}
