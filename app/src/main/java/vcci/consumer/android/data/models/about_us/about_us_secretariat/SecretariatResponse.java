package vcci.consumer.android.data.models.about_us.about_us_secretariat;

import com.google.gson.annotations.SerializedName;

public class SecretariatResponse{

	@SerializedName("about")
	private Secretariat secretariat;

	@SerializedName("error")
	private int error;

	public void setSecretariat(Secretariat secretariat){
		this.secretariat = secretariat;
	}

	public Secretariat getSecretariat(){
		return secretariat;
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
			"SecretariatResponse{" + 
			"secretariat = '" + secretariat + '\'' +
			",error = '" + error + '\'' + 
			"}";
		}
}