package vcci.consumer.android.data.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("error")
	private int error;

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
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
			"CategoriesResponse{" + 
			"categories = '" + categories + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}