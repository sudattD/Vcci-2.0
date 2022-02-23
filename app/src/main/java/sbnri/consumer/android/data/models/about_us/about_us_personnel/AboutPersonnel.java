package sbnri.consumer.android.data.models.about_us.about_us_personnel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutPersonnel {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("title")
	private String title;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
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