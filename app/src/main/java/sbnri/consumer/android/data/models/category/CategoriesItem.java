package sbnri.consumer.android.data.models.category;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem{

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public CategoriesItem() {
	}

	public CategoriesItem(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}