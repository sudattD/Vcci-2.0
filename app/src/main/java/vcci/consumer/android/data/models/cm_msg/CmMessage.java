package vcci.consumer.android.data.models.cm_msg;

import com.google.gson.annotations.SerializedName;

public class CmMessage{

	@SerializedName("data")
	private String data;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
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
			"CmMessage{" + 
			"data = '" + data + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}