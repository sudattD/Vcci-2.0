package vcci.consumer.android.data.models.about_us.about_us_content;

import com.google.gson.annotations.SerializedName;

public class About{

	@SerializedName("data")
	private String data;

	@SerializedName("title")
	private String title;

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
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
			"AboutPersonnel{" +
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}