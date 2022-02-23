package sbnri.consumer.android.data.models.events.event_list;

import com.google.gson.annotations.SerializedName;

public class EventListResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("events")
	private Events events;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setEvents(Events events){
		this.events = events;
	}

	public Events getEvents(){
		return events;
	}

	@Override
 	public String toString(){
		return 
			"EventListResponse{" + 
			"error = '" + error + '\'' + 
			",events = '" + events + '\'' + 
			"}";
		}
}