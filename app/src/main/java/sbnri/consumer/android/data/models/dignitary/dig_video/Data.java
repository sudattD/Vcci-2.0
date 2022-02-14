package sbnri.consumer.android.data.models.dignitary.dig_video;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("title")
	private String title;

	@SerializedName("youtube_id")
	private String youtubeId;

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

	public void setYoutubeId(String youtubeId){
		this.youtubeId = youtubeId;
	}

	public String getYoutubeId(){
		return youtubeId;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"video_url = '" + videoUrl + '\'' + 
			",title = '" + title + '\'' + 
			",youtube_id = '" + youtubeId + '\'' + 
			"}";
		}
}