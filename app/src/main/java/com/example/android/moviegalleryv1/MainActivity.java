package com.example.android.moviegalleryv1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.main_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getPopularMovies();
    }

    private void getPopularMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "04a5984bb20ef987a182de11ee3810b4");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiService asService = restAdapter.create(ApiService.class);
        asService.getPopularMovies(new Callback<TheMovie.MovieResult>() {
            @Override
            public void success(TheMovie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgView;
        public MovieViewHolder(View itemView){
            super(itemView);
            imgView = (ImageView)itemView.findViewById(R.id.main_imageView);
        }
    }

    public static class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>{
        private List<TheMovie> mTheMovieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public MoviesAdapter(Context context){
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);

        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){
            View view = mInflater.inflate(R.layout.row_movie, parent,false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mTheMovieList.get(position));
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position){
            TheMovie theMovie = mTheMovieList.get(position);
            Picasso.with(mContext)
                    .load(theMovie.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(holder.imgView);
        }

        @Override
        public int getItemCount() {
            return (mTheMovieList == null) ? 0 : mTheMovieList.size();
        }

        public void setMovieList(List<TheMovie> movieList){
            this.mTheMovieList = new ArrayList<>();
            this.mTheMovieList.addAll(movieList);
            notifyDataSetChanged();
        }
    }
}
