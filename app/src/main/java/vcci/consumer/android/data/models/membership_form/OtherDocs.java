package vcci.consumer.android.data.models.membership_form;

import com.google.gson.annotations.SerializedName;

public class OtherDocs {

	@SerializedName("title")
	private String title;

	@SerializedName("data")
	private String data;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "OtherDocs{" +
				"title='" + title + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}