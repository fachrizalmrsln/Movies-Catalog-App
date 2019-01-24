package com.example.zul.moviesmade.model;

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

    public Favorite(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Favorite(String id, String title, String vote_average, String release_date,
                    String over_view, String popular, String vote_count, String poster_path,
                    String backdrop_path) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.over_view = over_view;
        this.popular = popular;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
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

    public String getRelease_date() {
        return release_date;
    }

    public String getOver_view() {
        return over_view;
    }

    public String getPopular() {
        return popular;
    }

    public String getVote_count() {
        return vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

}
