package com.example.litaos.omdb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View movieView;
        TextView movieTitle;
        AppCompatImageView movieThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieView = itemView;
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieThumbnail = itemView.findViewById(R.id.movie_thumbnail);
        }
    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item_view,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.movieView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            Movie movie = movieList.get(position);
            Intent intent = new Intent(viewGroup.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.KEY_MOVIE, movie);
            viewGroup.getContext().startActivity(intent);
            Toast.makeText(view.getContext(), "Clicked" + position, Toast.LENGTH_SHORT).show();
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int i) {
        Movie movie = movieList.get(i);
        viewHolder.movieTitle.setText(movie.title);
        viewHolder.movieThumbnail.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
