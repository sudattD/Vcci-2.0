package vcci.consumer.android.data.models.circulars.circular_detail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import vcci.consumer.android.data.models.newsDetail.DetailPhotos;


public class CircularDetails {

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("venue")
	private String venue;

	@SerializedName("event_id")
	private String eventId;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("asso_with")
	private String assoWith;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

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

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return eventId;
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

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
			"EventDetails{" + 
			"end_date = '" + endDate + '\'' + 
			",venue = '" + venue + '\'' + 
			",event_id = '" + eventId + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",asso_with = '" + assoWith + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",event_time = '" + eventTime + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}