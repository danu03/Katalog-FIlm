package com.danusuhendra.catalogmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailFilmActivity extends AppCompatActivity {

    public static String EXTRAS_JSON = "";

    private JSONObject json;

    private TextView title, year, overview;
    private ImageView backdrop, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        title = (TextView) findViewById(R.id.film_title);
        year = (TextView) findViewById(R.id.film_year);
        overview = (TextView) findViewById(R.id.overview);

        backdrop = (ImageView) findViewById(R.id.backdrop_image);
        poster = (ImageView) findViewById(R.id.poster_image);

        EXTRAS_JSON = getIntent().getStringExtra(EXTRAS_JSON);

        if (!EXTRAS_JSON.equals("")){
            try {
                json = new JSONObject(EXTRAS_JSON);
                Film film = new Film(json);
                getImage(film);
                title.setText(film.getJudul());
                year.setText(film.getYear());
                overview.setText(film.getOverview());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void getImage(Film film){
        if (film.getUrl_gambar()!=null){
            String url_poster = "https://image.tmdb.org/t/p/w92/"+film.getUrl_gambar();
            Picasso.with(this).load(url_poster).into(poster);
        }
        if (film.getBackdrop()!=null){
            String url_backdrop = "https://image.tmdb.org/t/p/w300/"+film.getBackdrop();
            Picasso.with(this).load(url_backdrop).into(backdrop);
        }
    }
}
