package vcci.consumer.android.data.models.events.event_detail;

import com.google.gson.annotations.SerializedName;

public class EventDetailResponse{

	@SerializedName("event_details")
	private EventDetails eventDetails;

	@SerializedName("error")
	private int error;

	public void setEventDetails(EventDetails eventDetails){
		this.eventDetails = eventDetails;
	}

	public EventDetails getEventDetails(){
		return eventDetails;
	}

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"EventDetailResponse{" + 
			"event_details = '" + eventDetails + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}