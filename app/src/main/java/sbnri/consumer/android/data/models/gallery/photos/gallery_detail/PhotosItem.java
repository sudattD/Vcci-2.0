package sbnri.consumer.android.data.models.gallery.photos.gallery_detail;

import com.google.gson.annotations.SerializedName;

public class PhotosItem{

	@SerializedName("gallery_image")
	private String galleryImage;

	@SerializedName("gallery_photo_id")
	private String galleryPhotoId;

	@SerializedName("gallery_thumb")
	private String galleryThumb;

	public void setGalleryImage(String galleryImage){
		this.galleryImage = galleryImage;
	}

	public String getGalleryImage(){
		return galleryImage;
	}

	public void setGalleryPhotoId(String galleryPhotoId){
		this.galleryPhotoId = galleryPhotoId;
	}

	public String getGalleryPhotoId(){
		return galleryPhotoId;
	}

	public void setGalleryThumb(String galleryThumb){
		this.galleryThumb = galleryThumb;
	}

	public String getGalleryThumb(){
		return galleryThumb;
	}

	@Override
 	public String toString(){
		return 
			"PhotosItem{" + 
			"gallery_image = '" + galleryImage + '\'' + 
			",gallery_photo_id = '" + galleryPhotoId + '\'' + 
			",gallery_thumb = '" + galleryThumb + '\'' + 
			"}";
		}
}