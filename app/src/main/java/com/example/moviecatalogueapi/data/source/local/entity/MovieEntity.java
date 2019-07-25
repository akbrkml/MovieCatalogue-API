package com.example.moviecatalogueapi.data.source.local.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "movie")
public class MovieEntity {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;


    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "favorited")
    private boolean favorited = false;

    public MovieEntity(@NonNull Integer id, String posterPath, String overview, String releaseDate, String originalTitle, String title, String backdropPath, Double voteAverage, Boolean favorited) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        if (favorited != null) {
            this.favorited = favorited;
        }
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

}
