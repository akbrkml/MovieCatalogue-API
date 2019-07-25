package com.example.moviecatalogueapi.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tv_show")
public class TvShowEntity {

	@PrimaryKey()
	@NonNull
	@ColumnInfo(name = "id")
	private Integer id;

	public TvShowEntity(@NonNull Integer id, String firstAirDate, String overview, String posterPath, String backdropPath, String originalName, double voteAverage, String name, Boolean favorited) {
		this.id = id;
		this.firstAirDate = firstAirDate;
		this.overview = overview;
		this.posterPath = posterPath;
		this.backdropPath = backdropPath;
		this.originalName = originalName;
		this.voteAverage = voteAverage;
		this.name = name;
		if (favorited != null) {
			this.favorited = favorited;
		}
	}

	@ColumnInfo(name = "first_air_date")
	private String firstAirDate;

	@ColumnInfo(name = "overview")
	private String overview;

	@ColumnInfo(name = "poster_path")
	private String posterPath;

	@ColumnInfo(name = "backdrop_path")
	private String backdropPath;

	@ColumnInfo(name = "original_name")
	private String originalName;

	@ColumnInfo(name = "vote_average")
	private double voteAverage;

	@ColumnInfo(name = "name")
	private String name;

	@ColumnInfo(name = "favorited")
	private boolean favorited = false;

	public void setFirstAirDate(String firstAirDate){
		this.firstAirDate = firstAirDate;
	}

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public void setBackdropPath(String backdropPath){
		this.backdropPath = backdropPath;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public void setOriginalName(String originalName){
		this.originalName = originalName;
	}

	public String getOriginalName(){
		return originalName;
	}

	public void setVoteAverage(double voteAverage){
		this.voteAverage = voteAverage;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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