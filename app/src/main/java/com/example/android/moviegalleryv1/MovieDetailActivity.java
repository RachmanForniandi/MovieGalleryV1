package com.example.android.moviegalleryv1;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "The movie";

    private TheMovie mMovie;
    ImageView imgBackdrop;
    ImageView imgPoster;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)){
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        }else {
            throw  new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout_detail);
        toolbarLayout.setTitle(mMovie.getTitle());

        imgBackdrop =(ImageView)findViewById(R.id.backdrop_detail);
        tvTitle = (TextView)findViewById(R.id.movie_title);
        tvDescription =(TextView)findViewById(R.id.movie_description);
        imgPoster =(ImageView)findViewById(R.id.movie_poster);

        tvTitle.setText(mMovie.getTitle());
        tvDescription.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(imgPoster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(imgBackdrop);

    }
}
