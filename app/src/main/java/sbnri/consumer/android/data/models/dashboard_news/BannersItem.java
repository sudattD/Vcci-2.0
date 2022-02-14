package sbnri.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannersItem implements Serializable {

	@SerializedName("banner_id")
	private String bannerId;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	public void setBannerId(String bannerId){
		this.bannerId = bannerId;
	}

	public String getBannerId(){
		return bannerId;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"BannersItem{" + 
			"banner_id = '" + bannerId + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}