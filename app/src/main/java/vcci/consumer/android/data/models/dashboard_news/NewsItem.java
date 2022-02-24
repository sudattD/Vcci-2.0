package vcci.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

public class NewsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	@SerializedName("news_id")
	private String newsId;

	@SerializedName("views")
	private String views;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setNewsId(String newsId){
		this.newsId = newsId;
	}

	public String getNewsId(){
		return newsId;
	}

	public void setViews(String views){
		this.views = views;
	}

	public String getViews(){
		return views;
	}

	@Override
 	public String toString(){
		return 
			"NewsItem{" + 
			"date = '" + date + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			",news_id = '" + newsId + '\'' + 
			",views = '" + views + '\'' + 
			"}";
		}
}