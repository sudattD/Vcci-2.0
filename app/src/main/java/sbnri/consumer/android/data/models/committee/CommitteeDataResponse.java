package sbnri.consumer.android.data.models.committee;

import com.google.gson.annotations.SerializedName;

public class CommitteeDataResponse{

	@SerializedName("committees")
	private Committees committees;

	@SerializedName("error")
	private int error;

	public void setCommittees(Committees committees){
		this.committees = committees;
	}

	public Committees getCommittees(){
		return committees;
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
			"CommitteeDataResponse{" + 
			"committees = '" + committees + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}