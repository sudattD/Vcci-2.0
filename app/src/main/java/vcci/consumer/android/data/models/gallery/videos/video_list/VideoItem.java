package vcci.consumer.android.data.models.gallery.videos.video_list;

import com.google.gson.annotations.SerializedName;

public class VideoItem {

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("title")
	private String title;

	@SerializedName("youtube_id")
	private String youtubeId;

	@SerializedName("video_id")
	private String videoId;

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

	public void setVideoId(String videoId){
		this.videoId = videoId;
	}

	public String getVideoId(){
		return videoId;
	}

	@Override
 	public String toString(){
		return 
			"VideoItem{" +
			"video_url = '" + videoUrl + '\'' + 
			",title = '" + title + '\'' + 
			",youtube_id = '" + youtubeId + '\'' + 
			",video_id = '" + videoId + '\'' + 
			"}";
		}
}