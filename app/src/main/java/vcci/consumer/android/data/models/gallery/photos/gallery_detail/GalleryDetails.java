package vcci.consumer.android.data.models.gallery.photos.gallery_detail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import vcci.consumer.android.data.models.newsDetail.DetailPhotos;


public class GalleryDetails{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("venue")
	private String venue;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("asso_with")
	private String assoWith;

	@SerializedName("description")
	private String description;

	@SerializedName("gallery_id")
	private String galleryId;

	@SerializedName("title")
	private String title;

	/*@SerializedName("photos")
	private List<PhotosItem> photos;*/

	@SerializedName("event_time")
	private String eventTime;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("photos")
	private List<DetailPhotos> photos;

	public List<DetailPhotos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<DetailPhotos> photos) {
		this.photos = photos;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setVenue(String venue){
		this.venue = venue;
	}

	public String getVenue(){
		return venue;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setAssoWith(String assoWith){
		this.assoWith = assoWith;
	}

	public String getAssoWith(){
		return assoWith;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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

	/*public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}*/

	public void setEventTime(String eventTime){
		this.eventTime = eventTime;
	}

	public String getEventTime(){
		return eventTime;
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
			"GalleryDetails{" + 
			"end_date = '" + endDate + '\'' + 
			",venue = '" + venue + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",asso_with = '" + assoWith + '\'' + 
			",description = '" + description + '\'' + 
			",gallery_id = '" + galleryId + '\'' + 
			",title = '" + title + '\'' + 
			",event_time = '" + eventTime + '\'' +
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}