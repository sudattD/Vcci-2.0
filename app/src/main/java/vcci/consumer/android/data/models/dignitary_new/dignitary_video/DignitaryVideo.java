package vcci.consumer.android.data.models.dignitary_new.dignitary_video;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DignitaryVideo {

	@SerializedName("data")
	private List<DataItemDignitaryVideo> data;

	@SerializedName("title")
	private String title;

	public void setData(List<DataItemDignitaryVideo> data){
		this.data = data;
	}

	public List<DataItemDignitaryVideo> getData(){
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