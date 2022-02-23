package vcci.consumer.android.data.models.dignitary_new.dignitary_photos;

import com.google.gson.annotations.SerializedName;

public class DignitaryPhotoResponseNew {

	@SerializedName("dignitary")
	private DignitaryPhoto dignitary;

	@SerializedName("error")
	private int error;

	public void setDignitaryPhoto(DignitaryPhoto dignitary){
		this.dignitary = dignitary;
	}

	public DignitaryPhoto getDignitaryPhoto(){
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
			"DignitaryPhotoResponse{" + 
			"dignitary = '" + dignitary + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}