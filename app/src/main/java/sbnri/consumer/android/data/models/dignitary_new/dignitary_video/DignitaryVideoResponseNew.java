package sbnri.consumer.android.data.models.dignitary_new.dignitary_video;

import com.google.gson.annotations.SerializedName;

public class DignitaryVideoResponseNew {

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