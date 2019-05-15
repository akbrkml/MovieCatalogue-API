package com.example.moviecatalogueapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvShowResponse implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TvShow> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<TvShow> results){
		this.results = results;
	}

	public List<TvShow> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.page);
		dest.writeInt(this.totalPages);
		dest.writeTypedList(this.results);
		dest.writeInt(this.totalResults);
	}

	public TvShowResponse() {
	}

	private TvShowResponse(Parcel in) {
		this.page = in.readInt();
		this.totalPages = in.readInt();
		this.results = in.createTypedArrayList(TvShow.CREATOR);
		this.totalResults = in.readInt();
	}

	public static final Parcelable.Creator<TvShowResponse> CREATOR = new Parcelable.Creator<TvShowResponse>() {
		@Override
		public TvShowResponse createFromParcel(Parcel source) {
			return new TvShowResponse(source);
		}

		@Override
		public TvShowResponse[] newArray(int size) {
			return new TvShowResponse[size];
		}
	};
}