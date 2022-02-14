package sbnri.consumer.android.data.models.gallery.photos.gallery_detail;

import com.google.gson.annotations.SerializedName;

public class GalleryDetailResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("gallery_details")
	private GalleryDetails galleryDetails;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setGalleryDetails(GalleryDetails galleryDetails){
		this.galleryDetails = galleryDetails;
	}

	public GalleryDetails getGalleryDetails(){
		return galleryDetails;
	}

	@Override
 	public String toString(){
		return 
			"GalleryDetailResponse{" + 
			"error = '" + error + '\'' + 
			",gallery_details = '" + galleryDetails + '\'' + 
			"}";
		}
}