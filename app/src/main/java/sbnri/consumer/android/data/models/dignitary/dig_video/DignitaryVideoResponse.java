package sbnri.consumer.android.data.models.dignitary.dig_video;

import com.google.gson.annotations.SerializedName;

import sbnri.consumer.android.data.models.dignitary_new.dignitary_video.DignitaryVideo;


public class DignitaryVideoResponse{

	@SerializedName("dignitary")
	private DignitaryVideo dignitary;

	@SerializedName("error")
	private int error;

	public void setDignitaryVideo(DignitaryVideo dignitary){
		this.dignitary = dignitary;
	}

	public DignitaryVideo getDignitaryVideo(){
		return dignitary;
	}

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"DignitaryVideoResponse{" + 
			"dignitary = '" + dignitary + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}