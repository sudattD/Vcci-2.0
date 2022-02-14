package sbnri.consumer.android.data.models.events.event_list;

import com.google.gson.annotations.SerializedName;

public class EventItem {

	@SerializedName("event_id")
	private String eventId;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	@SerializedName("start_date")
	private String startDate;

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
			"EventItem{" +
			"event_id = '" + eventId + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}