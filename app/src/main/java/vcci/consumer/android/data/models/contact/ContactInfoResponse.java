package vcci.consumer.android.data.models.contact;

import com.google.gson.annotations.SerializedName;

public class ContactInfoResponse{

	@SerializedName("contact")
	private Contact contact;

	@SerializedName("error")
	private int error;

	public void setContact(Contact contact){
		this.contact = contact;
	}

	public Contact getContact(){
		return contact;
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
			"ContactInfoResponse{" + 
			"contact = '" + contact + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}