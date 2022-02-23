package sbnri.consumer.android.data.models.dignitary.dig_photo;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("popup")
	private String popup;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	public void setPopup(String popup){
		this.popup = popup;
	}

	public String getPopup(){
		return popup;
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
			"Data{" + 
			"popup = '" + popup + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}