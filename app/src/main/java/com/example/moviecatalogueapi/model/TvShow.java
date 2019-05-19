package com.example.moviecatalogueapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class TvShow implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	private int key;

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.firstAirDate);
		dest.writeString(this.overview);
		dest.writeString(this.posterPath);
		dest.writeString(this.name);
		dest.writeInt(this.id);
		dest.writeInt(this.key);
	}

	public TvShow() {
	}

	private TvShow(Parcel in) {
		this.firstAirDate = in.readString();
		this.overview = in.readString();
		this.posterPath = in.readString();
		this.name = in.readString();
		this.id = in.readInt();
		this.key = in.readInt();
	}

	public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
		@Override
		public TvShow createFromParcel(Parcel source) {
			return new TvShow(source);
		}

		@Override
		public TvShow[] newArray(int size) {
			return new TvShow[size];
		}
	};
}