package vcci.consumer.android.data.models.gallery.photos.gallery_list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gallery{

	@SerializedName("data")
	private List<GalleryItem> data;

	@SerializedName("title")
	private String title;

	public void setData(List<GalleryItem> data){
		this.data = data;
	}

	public List<GalleryItem> getData(){
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
			"VideoGallery{" +
			"data = '" + data + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}