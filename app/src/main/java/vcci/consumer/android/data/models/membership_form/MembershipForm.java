package vcci.consumer.android.data.models.membership_form;

import com.google.gson.annotations.SerializedName;

public class MembershipForm {

	@SerializedName("download")
	private String download;

	@SerializedName("view")
	private String view;

	@SerializedName("sr.")
	private String sr;

	@SerializedName("forms")
	private String forms;

	public void setDownload(String download){
		this.download = download;
	}

	public String getDownload(){
		return download;
	}

	public void setView(String view){
		this.view = view;
	}

	public String getView(){
		return view;
	}

	public void setSr(String sr){
		this.sr = sr;
	}

	public String getSr(){
		return sr;
	}

	public void setForms(String forms){
		this.forms = forms;
	}

	public String getForms(){
		return forms;
	}

	@Override
 	public String toString(){
		return 
			"MembershipForm{" +
			"download = '" + download + '\'' + 
			",view = '" + view + '\'' + 
			",sr. = '" + sr + '\'' + 
			",forms = '" + forms + '\'' + 
			"}";
		}
}