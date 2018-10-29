package com.danusuhendra.catalogmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class FilmAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Film> myFilm = new ArrayList<>();

    public FilmAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Film> film){
        myFilm = film;
        notifyDataSetChanged();
    }

    public void addItem(Film film){
        myFilm.add(film);
        notifyDataSetChanged();
    }

    public void clearData(){
        myFilm.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (myFilm==null) return 0;
        return myFilm.size();
    }

    @Override
    public Film getItem(int i) {
        return myFilm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_film, null);
            holder.judulFilm = (TextView) convertView.findViewById(R.id.judul_film);
            holder.overview = (TextView) convertView.findViewById(R.id.overview_film);
            holder.gambarFilm = (ImageView) convertView.findViewById(R.id.poster_film);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.judulFilm.setText(myFilm.get(position).getJudul());
        holder.overview.setText(myFilm.get(position).getOverview());

        //url poster image
        String url = "https://image.tmdb.org/t/p/w92/"+myFilm.get(position).getUrl_gambar();
        //set image with poster
        Picasso.with(convertView.getContext()).load(url).into(holder.gambarFilm);

        return convertView;
    }

    private static class ViewHolder{
        TextView judulFilm, overview;
        ImageView gambarFilm;
    }
}

