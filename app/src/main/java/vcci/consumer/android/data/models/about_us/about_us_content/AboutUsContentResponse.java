package vcci.consumer.android.data.models.about_us.about_us_content;

import com.google.gson.annotations.SerializedName;

public class AboutUsContentResponse{

	@SerializedName("about")
	private About about;

	@SerializedName("error")
	private int error;

	public void setAbout(About about){
		this.about = about;
	}

	public About getAbout(){
		return about;
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
			"AboutUsContentResponse{" + 
			"about = '" + about + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}