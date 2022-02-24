package vcci.consumer.android.data.models.dignitary_new.dignitary_photos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DignitaryPhoto {

	@SerializedName("data")
	private List<DataItemDignitaryPhoto> data;

	@SerializedName("title")
	private String title;

	public void setData(List<DataItemDignitaryPhoto> data){
		this.data = data;
	}

	public List<DataItemDignitaryPhoto> getData(){
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
			"Dignitary{" + 
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}