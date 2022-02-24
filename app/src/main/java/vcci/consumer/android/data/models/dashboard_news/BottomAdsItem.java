package vcci.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

public class BottomAdsItem{

	@SerializedName("thumb_image")
	private String thumbImage;

	@SerializedName("popup_image")
	private String popupImage;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public void setThumbImage(String thumbImage){
		this.thumbImage = thumbImage;
	}

	public String getThumbImage(){
		return thumbImage;
	}

	public void setPopupImage(String popupImage){
		this.popupImage = popupImage;
	}

	public String getPopupImage(){
		return popupImage;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"BottomAdsItem{" + 
			"thumb_image = '" + thumbImage + '\'' + 
			",popup_image = '" + popupImage + '\'' + 
			",title = '" + title + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}