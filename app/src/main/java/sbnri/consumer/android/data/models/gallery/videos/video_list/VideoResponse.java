package sbnri.consumer.android.data.models.gallery.videos.video_list;

import com.google.gson.annotations.SerializedName;

public class VideoResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("gallery")
	private VideoGallery videoGallery;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setVideoGallery(VideoGallery videoGallery){
		this.videoGallery = videoGallery;
	}

	public VideoGallery getVideoGallery(){
		return videoGallery;
	}

	@Override
 	public String toString(){
		return 
			"VideoResponse{" + 
			"error = '" + error + '\'' + 
			",videoGallery = '" + videoGallery + '\'' +
			"}";
		}
}