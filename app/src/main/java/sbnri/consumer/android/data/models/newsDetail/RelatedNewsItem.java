package sbnri.consumer.android.data.models.newsDetail;

import com.google.gson.annotations.SerializedName;

public class RelatedNewsItem{

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	@SerializedName("news_id")
	private String newsId;

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

	@Override
 	public String toString(){
		return 
			"RelatedNewsItem{" + 
			"thumb = '" + thumb + '\'' + 
			",title = '" + title + '\'' + 
			",news_id = '" + newsId + '\'' + 
			"}";
		}
}