package com.example.litaos.omdb;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SearchService {

    private static final String OMDB_URL = "http://www.omdbapi.com";
    private static OmdbApi omdbApi;

    public static class MovieList {
        public List<Movie> movieList;
        public String response;

        @Override
        public String toString() {
            return "Result{" +
                    "movieList=" + movieList +
                    ", response='" + response + '\''
                    +"}";
        }
    }

    public interface OmdbApi {
        @GET("?type=movie")
        Call<MovieList> getMovieList(@Query("s") String title);
    }

    private static OmdbApi getOmdbApi() {
        if(omdbApi == null) {
            OkHttpClient.Builder httpClient =
                    new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", BuildConfig.API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder().baseUrl(OMDB_URL).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create()).build();
            omdbApi = retrofit.create(OmdbApi.class);
        }
        return omdbApi;
    }

    public static MovieList searchMovieList(String title) throws IOException {
        Call<MovieList> call = getOmdbApi().getMovieList(title);
        return call.execute().body();
    }

}
