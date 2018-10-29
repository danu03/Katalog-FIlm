package com.danusuhendra.catalogmovie;

import org.json.JSONObject;



public class Film {

    private String judul;
    private String overview;
    private String url_gambar;
    private String year;
    private String backdrop;

    public Film(JSONObject object){
        try{
            String title = object.getString("original_title");
            String ulasan = object.getString("overview");
            String url = object.getString("poster_path");
            String tahun = object.getString("release_date");
            String background = object.getString("backdrop_path");
            this.judul = title;
            this.overview = ulasan;
            this.url_gambar = url;
            if (!tahun.equals("")){
                this.year = tahun.substring(0,4);
            } else{
                year = "";
            }
            this.backdrop = background;

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }

    public void setUrl_gambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
