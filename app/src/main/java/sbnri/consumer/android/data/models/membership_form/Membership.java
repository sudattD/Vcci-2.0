package sbnri.consumer.android.data.models.membership_form;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Membership{

	@SerializedName("data")
	private List<MembershipForm> data;

	@SerializedName("title")
	private String title;

	public void setData(List<MembershipForm> data){
		this.data = data;
	}

	public List<MembershipForm> getData(){
		return data;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"Membership{" + 
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}