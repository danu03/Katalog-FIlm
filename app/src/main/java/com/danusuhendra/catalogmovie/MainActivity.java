package com.danusuhendra.catalogmovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText fieldSearch;
    private Button search;
    private ImageButton clear;
    private ListView listView;
    private String API_KEY = "326e0dabab4ee30ac7aa8a8f262e4664";
    String TAG = "Katalog Film";
    private ProgressDialog progress;
    private TextView status;

    private FilmAdapter adapter;

    private JSONObject json;
//    private JSONObject jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldSearch = (EditText) findViewById(R.id.field_search);
        search = (Button) findViewById(R.id.btn_cari);
        listView = (ListView) findViewById(R.id.list_film);
        clear = (ImageButton) findViewById(R.id.btn_clear);
        status = (TextView) findViewById(R.id.status_list);

        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setTitle("Catalogue Movie App");
        progress.setMessage("Sedang mengambil data...");

        adapter = new FilmAdapter(this);

        search.setOnClickListener(this);
        clear.setOnClickListener(this);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&sort_by=popularity.desc";
        progress.show();
        getFilm(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getApplicationContext(), DetailFilmActivity.class);
                String send = "";
                try {
                    send = json.getJSONArray("results").getJSONObject(position).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra(DetailFilmActivity.EXTRAS_JSON, send);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_cari){
            String query = fieldSearch.getText().toString();
            if (!TextUtils.isEmpty(query)){
                String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY;
                progress.show();
                getFilm(url, query);
            }
        }
        if (view.getId()==R.id.btn_clear){
            fieldSearch.getText().clear();
            fieldSearch.clearFocus();
        }
    }

    private void getFilm(String alamat, final String query) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> list_film = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");
        String url = alamat+"&language=en-US&query="+query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    Log.d(TAG, "result: "+result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json =  getResult.getJSONObject(i);
                        Film film = new Film(json);
                        list_film.add(film);
                        Log.d(TAG, "year: "+list_film.get(i).getYear());
                        Log.d(TAG, "backdrop: "+list_film.get(i).getBackdrop());
                    }
                    adapter.setData(list_film);
                    Log.d(TAG, "size adapter: "+adapter.getCount());
                    listView.setAdapter(adapter);
                    status.setText("Displaying result for "+query);
                    progress.dismiss();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getFilm(String url) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> list_film = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    Log.d(TAG, "result: "+result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json =  getResult.getJSONObject(i);
                        Film film = new Film(json);
                        list_film.add(film);
                        Log.d(TAG, "year: "+list_film.get(i).getYear());
                        Log.d(TAG, "backdrop: "+list_film.get(i).getBackdrop());
                    }
                    adapter.setData(list_film);
                    Log.d(TAG, "size adapter: "+adapter.getCount());
                    listView.setAdapter(adapter);
                    progress.dismiss();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
