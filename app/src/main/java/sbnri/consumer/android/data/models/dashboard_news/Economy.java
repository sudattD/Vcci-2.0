package sbnri.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Economy{

	@SerializedName("news")
	private List<NewsItem> news;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("title")
	private String title;

	public void setNews(List<NewsItem> news){
		this.news = news;
	}

	public List<NewsItem> getNews(){
		return news;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
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
			"Economy{" + 
			"news = '" + news + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}