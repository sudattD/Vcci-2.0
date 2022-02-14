package sbnri.consumer.android.data.models.newsDetail;

import com.google.gson.annotations.SerializedName;

public class NewsDetailResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("news_details")
	private NewsDetails newsDetails;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setNewsDetails(NewsDetails newsDetails){
		this.newsDetails = newsDetails;
	}

	public NewsDetails getNewsDetails(){
		return newsDetails;
	}

	@Override
 	public String toString(){
		return 
			"NewsDetailResponse{" + 
			"error = '" + error + '\'' + 
			",news_details = '" + newsDetails + '\'' + 
			"}";
		}
}