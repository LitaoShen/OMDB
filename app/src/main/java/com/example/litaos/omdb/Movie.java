package com.example.litaos.omdb;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public String imdbid;
    public String title;
    public String poster;
    public String year;
    public String runtime;
    public String genre;
    public String plot;
    public int src;

    public Movie(String title, int src) {
        this.title = title;
        this.src = src;
    }

    protected Movie(Parcel src) {
        imdbid = src.readString();
        title = src.readString();
        poster = src.readString();
        year = src.readString();
        runtime = src.readString();
        genre = src.readString();
        plot = src.readString();
    }

    @Override
    public String toString() {
        return "\nMovie{" +
                "imdbID='" + imdbid + '\'' +
                "title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", year='" + year + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", plot='" + plot + '\'' +
                "}";

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdbid);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(year);
        dest.writeString(runtime);
        dest.writeString(genre);
        dest.writeString(plot);
    }
}
