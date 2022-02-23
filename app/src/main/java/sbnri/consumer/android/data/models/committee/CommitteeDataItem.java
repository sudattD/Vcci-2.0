package sbnri.consumer.android.data.models.committee;

import com.google.gson.annotations.SerializedName;

public class CommitteeDataItem {

	@SerializedName("sr.")
	private String srNo;

	@SerializedName("name")
	private String name;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("phone")
	private String phone;

	@SerializedName("phone1")
	private String phone1;

	@SerializedName("email")
	private String email;

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "CommitteeDataItem{" +
				"srNo='" + srNo + '\'' +
				", name='" + name + '\'' +
				", companyName='" + companyName + '\'' +
				", mobile='" + mobile + '\'' +
				", phone='" + phone + '\'' +
				", phone1='" + phone1 + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}