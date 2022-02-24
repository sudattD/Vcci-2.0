package vcci.consumer.android.data.models.events.event_list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Events{

	@SerializedName("data")
	private List<EventItem> data;

	@SerializedName("title")
	private String title;

	public void setData(List<EventItem> data){
		this.data = data;
	}

	public List<EventItem> getData(){
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
			"Events{" + 
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}