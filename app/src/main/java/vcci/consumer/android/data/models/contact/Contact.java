package vcci.consumer.android.data.models.contact;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contact{

	@SerializedName("address")
	private String address;

	@SerializedName("mobile")
	private List<String> mobile;

	@SerializedName("title")
	private String title;

	@SerializedName("email")
	private List<String> email;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setMobile(List<String> mobile){
		this.mobile = mobile;
	}

	public List<String> getMobile(){
		return mobile;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setEmail(List<String> email){
		this.email = email;
	}

	public List<String> getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Contact{" + 
			"address = '" + address + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",title = '" + title + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}