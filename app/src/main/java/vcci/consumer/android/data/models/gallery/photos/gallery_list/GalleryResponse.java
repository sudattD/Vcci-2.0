package vcci.consumer.android.data.models.gallery.photos.gallery_list;

import com.google.gson.annotations.SerializedName;

public class GalleryResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("gallery")
	private Gallery gallery;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setGallery(Gallery gallery){
		this.gallery = gallery;
	}

	public Gallery getGallery(){
		return gallery;
	}

	@Override
 	public String toString(){
		return 
			"GalleryResponse{" + 
			"error = '" + error + '\'' + 
			",gallery = '" + gallery + '\'' + 
			"}";
		}
}