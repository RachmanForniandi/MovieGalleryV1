package com.example.android.moviegalleryv1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lenovo on 10/27/2017.
 */

public class TheMovie implements Parcelable{
    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    public TheMovie(){

    }

    protected TheMovie(Parcel in){
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
    }

    public static final Creator<TheMovie>CREATOR = new Creator<TheMovie>() {
        @Override
        public TheMovie createFromParcel(Parcel in) {
            return new TheMovie(in);
        }

        @Override
        public TheMovie[] newArray(int size) {
            return new TheMovie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500" + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
    }

    public static class MovieResult{
        private List<TheMovie> results;

        public List<TheMovie>getResults(){
            return results;
        }
    }
}
