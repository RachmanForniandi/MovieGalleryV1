package com.example.android.moviegalleryv1;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Lenovo on 10/27/2017.
 */

public interface ApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<TheMovie.MovieResult> cb);
}
