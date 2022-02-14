package sbnri.consumer.android.data.models.gallery.photos.gallery_list;

import com.google.gson.annotations.SerializedName;

public class GalleryItem {

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("gallery_id")
	private String galleryId;

	@SerializedName("title")
	private String title;

	@SerializedName("start_date")
	private String startDate;

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setGalleryId(String galleryId){
		this.galleryId = galleryId;
	}

	public String getGalleryId(){
		return galleryId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"GalleryItem{" +
			"thumb = '" + thumb + '\'' + 
			",gallery_id = '" + galleryId + '\'' + 
			",title = '" + title + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}