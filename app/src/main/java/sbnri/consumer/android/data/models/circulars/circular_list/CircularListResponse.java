package sbnri.consumer.android.data.models.circulars.circular_list;

import com.google.gson.annotations.SerializedName;

public class CircularListResponse{

	@SerializedName("error")
	private int error;

	@SerializedName("circulars")
	private Circulars circulars;

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setCirculars(Circulars circulars){
		this.circulars = circulars;
	}

	public Circulars getCirculars(){
		return circulars;
	}

	@Override
 	public String toString(){
		return 
			"CircularListResponse{" + 
			"error = '" + error + '\'' + 
			",circulars = '" + circulars + '\'' + 
			"}";
		}
}