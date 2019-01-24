package com.example.app2.model;

import android.database.Cursor;

import com.example.app2.database.DatabaseContract;
import com.example.app2.provider.ContractProvider;

/**
 * copyright zul
 **/

public class Favorite {

    private String id;
    private String title;
    private String vote_average;
    private String release_date;
    private String over_view;
    private String popular;
    private String vote_count;
    private String poster_path;
    private String backdrop_path;

    public Favorite(Cursor cursor) {
        this.id = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_ID);
        this.title = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_TITLE);
        this.vote_average = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_VOTE_AVERAGE);
        this.release_date = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_RELEASE_DATE);
        this.over_view = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_OVERVIEW);
        this.popular = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_POPULAR);
        this.vote_count = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_VOTE_COUNT);
        this.poster_path = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_POSTER_PATH);
        this.backdrop_path = ContractProvider.getColumnString(cursor,
                DatabaseContract.COLUMN_BACKDROP_PATH);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOver_view() {
        return over_view;
    }

    public void setOver_view(String over_view) {
        this.over_view = over_view;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

}
