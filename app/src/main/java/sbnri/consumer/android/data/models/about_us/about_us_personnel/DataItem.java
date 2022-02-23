package sbnri.consumer.android.data.models.about_us.about_us_personnel;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("address")
	private String address;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("year")
	private String year;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("commitee_id")
	private String commiteeId;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile_no")
	private String mobileNo;

	@SerializedName("designation")
	private String designation;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setCommiteeId(String commiteeId){
		this.commiteeId = commiteeId;
	}

	public String getCommiteeId(){
		return commiteeId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setDesignation(String designation){
		this.designation = designation;
	}

	public String getDesignation(){
		return designation;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"EventItem{" +
			"address = '" + address + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",year = '" + year + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",commitee_id = '" + commiteeId + '\'' + 
			",name = '" + name + '\'' + 
			",mobile_no = '" + mobileNo + '\'' + 
			",designation = '" + designation + '\'' + 
			",type = '" + type + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}