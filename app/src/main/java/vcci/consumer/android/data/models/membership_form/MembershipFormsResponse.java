package vcci.consumer.android.data.models.membership_form;

import com.google.gson.annotations.SerializedName;

public class MembershipFormsResponse{

	@SerializedName("membership")
	private Membership membership;

	@SerializedName("others")
	private OtherDocs others;

	@SerializedName("error")
	private int error;

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public OtherDocs getOthers() {
		return others;
	}

	public void setOthers(OtherDocs others) {
		this.others = others;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "MembershipFormsResponse{" +
				"membership=" + membership +
				", others=" + others +
				", error=" + error +
				'}';
	}
}