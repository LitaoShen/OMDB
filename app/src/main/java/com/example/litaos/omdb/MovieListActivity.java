package com.example.litaos.omdb;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_GENRE;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_IMDBID;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_PLOT;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_POSTER;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_RUNTIME;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_TITLE;
import static com.example.litaos.omdb.DatabaseHelper.DB_COLUMN_YEAR;

public class MovieListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<SearchService.MovieList>
{
    private static final String LOG_TAG = "MovieListActivity";
    private static final String API_URL = "http://www.omdbapi.com";
    private DatabaseHelper databaseHelper;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    EditText result;

    private List<Movie> movieList = new ArrayList<>();

    FragmentPagerAdapter fragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        databaseHelper = new DatabaseHelper(this, "Movies.db", null, 1);
        /*initMoviewlist();
        RecyclerView recyclerView = findViewById(R.id.movie_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MovieAdapter moviewAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(moviewAdapter);*/

        ViewPager viewPager = findViewById(R.id.fragment_view_pager);
        fragmentPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        AppCompatButton http = findViewById(R.id.http);
        http.setOnClickListener(v -> sendSearchRequest());
        result = findViewById(R.id.search_key_word);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void sendSearchRequest() {
        Bundle bundle = new Bundle();
        bundle.putString("movieTitle", "World");
        getSupportLoaderManager().restartLoader(1, bundle, this);
        //SearchService.OmdbApi omdbApi = SearchService.getOmdbApi();
        //Call<SearchService.MovieList> call = omdbApi.getMovieList("world");
        /*try {
            SearchService.MovieList movieList = call.execute().body();
            Log.i(LOG_TAG, "onResponse: " + movieList.movieList.size());
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "onResponse: " + e.toString());
        }*/


        /*call.enqueue(new Callback<SearchService.MovieList>() {
            @Override
            public void onResponse(Call<SearchService.MovieList> call, Response<SearchService.MovieList> response) {
                if(response.isSuccessful()) {
                    SearchService.MovieList movieList = response.body();
                    Log.i(LOG_TAG, "onResponse: " + movieList.movieList.size());
                }
            }

            @Override
            public void onFailure(Call<SearchService.MovieList> call, Throwable t) {
                Log.e(LOG_TAG, "onFailure: " + t.getMessage() );
            }
        });*/
        /*SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN_IMDBID, "tt1087856");
        contentValues.put(DB_COLUMN_TITLE, "Hello World");
        contentValues.put(DB_COLUMN_POSTER, "https://m.media-amazon.com/images/M/MV5BZGM5NjliODgtODVlOS00OWZmLWIzYzMtMTI2OWIzMTM1ZGRhXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg");
        contentValues.put(DB_COLUMN_YEAR, "2010");
        contentValues.put(DB_COLUMN_RUNTIME, "10 minutes");
        contentValues.put(DB_COLUMN_GENRE, "comic");
        contentValues.put(DB_COLUMN_PLOT, "Hello");
        db.insert("Movie", null, contentValues);*/

        /*new Thread(() -> {
            try {
                URL url = new URL("http://www.omdbapi.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes("t=WORLD&apikey=487b709d");
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                showSearchResult(sb.toString());
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    private void initMoviewlist() {
        for(int i = 0; i < 6; i++) {
           // movieList.add(new Movie("Movie Title: " + i));
        }
    }

    private void showSearchResult(String response) {
        runOnUiThread(() -> {
            result.setText(response);
        });
    }

    @NonNull
    @Override
    public Loader<SearchService.MovieList> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new SearchLoader(this, bundle.getString("movieTitle"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<SearchService.MovieList> loader, SearchService.MovieList movieList) {
        SearchService.MovieList searchRsultList = movieList;
        Log.i(LOG_TAG, "onLoadFinished movie list size: " + searchRsultList.movieList.size());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<SearchService.MovieList> loader) {

    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0: return MoviesFragment.newInstance(0, "All");
                case 1: return MoviesFragment.newInstance(1, "Favorite");
                default: break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return getString(R.string.all_fragment);
                case 1: return getString(R.string.favorite_fragment);
                default: break;
            }
            return getString(R.string.all_fragment);
        }
    }
}
