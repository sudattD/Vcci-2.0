package sbnri.consumer.android.data.models.about_us.about_us_secretariat;

import com.google.gson.annotations.SerializedName;

public class SecretariatItem {

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile_no")
	private String mobileNo;

	@SerializedName("description")
	private String description;

	@SerializedName("designation")
	private String designation;

	@SerializedName("email")
	private String email;

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDesignation(String designation){
		this.designation = designation;
	}

	public String getDesignation(){
		return designation;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"SecretariatItem{" +
			"thumb = '" + thumb + '\'' + 
			",name = '" + name + '\'' + 
			",mobile_no = '" + mobileNo + '\'' + 
			",description = '" + description + '\'' + 
			",designation = '" + designation + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}