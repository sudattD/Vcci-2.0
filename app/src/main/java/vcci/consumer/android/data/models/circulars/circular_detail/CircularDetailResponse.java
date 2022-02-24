package vcci.consumer.android.data.models.circulars.circular_detail;

import com.google.gson.annotations.SerializedName;

public class CircularDetailResponse {

	@SerializedName("circular_details")
	private CircularDetails eventDetails;

	@SerializedName("error")
	private int error;

	public void setEventDetails(CircularDetails eventDetails){
		this.eventDetails = eventDetails;
	}

	public CircularDetails getEventDetails(){
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