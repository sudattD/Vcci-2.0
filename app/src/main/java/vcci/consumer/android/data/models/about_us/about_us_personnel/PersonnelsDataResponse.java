package vcci.consumer.android.data.models.about_us.about_us_personnel;

import com.google.gson.annotations.SerializedName;

public class PersonnelsDataResponse{

	@SerializedName("about")
	private AboutPersonnel about;

	@SerializedName("error")
	private int error;

	public AboutPersonnel getAbout() {
		return about;
	}

	public void setAbout(AboutPersonnel about) {
		this.about = about;
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
			"PersonnelsDataResponse{" + 
			"about = '" + about + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}