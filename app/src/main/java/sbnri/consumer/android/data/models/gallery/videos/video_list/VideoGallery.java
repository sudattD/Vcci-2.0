package sbnri.consumer.android.data.models.gallery.videos.video_list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoGallery {

	@SerializedName("data")
	private List<VideoItem> data;

	@SerializedName("title")
	private String title;

	public void setData(List<VideoItem> data){
		this.data = data;
	}

	public List<VideoItem> getData(){
		return data;
	}

	public void setVideoTitle(String title){
		this.title = title;
	}

	public String getVideoTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"VideoGallery{" +
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}