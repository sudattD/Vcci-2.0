package sbnri.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

public class BulletinsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("title")
	private String title;

	@SerializedName("bulletin_id")
	private String bulletinId;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBulletinId(String bulletinId){
		this.bulletinId = bulletinId;
	}

	public String getBulletinId(){
		return bulletinId;
	}

	@Override
 	public String toString(){
		return 
			"BulletinsItem{" + 
			"image = '" + image + '\'' + 
			",video_url = '" + videoUrl + '\'' + 
			",title = '" + title + '\'' + 
			",bulletin_id = '" + bulletinId + '\'' + 
			"}";
		}
}