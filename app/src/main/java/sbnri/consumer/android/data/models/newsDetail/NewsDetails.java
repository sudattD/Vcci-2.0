package sbnri.consumer.android.data.models.newsDetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDetails{

	@SerializedName("date")
	private String date;

	@SerializedName("share_link")
	private String shareLink;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("next_news")
	private NextNews nextNews;

	@SerializedName("related_news")
	private List<RelatedNewsItem> relatedNews;

	@SerializedName("bottom_ads")
	private List<BottomAdsItem> bottomAds;

	@SerializedName("right_ad")
	private List<RightAdsItem> rightAds;

	@SerializedName("left_ad")
	private List<LeftAdsItem> leftAds;

	@SerializedName("description")
	private String description;

	@SerializedName("prev_news")
	private PrevNews prevNews;

	@SerializedName("display_name")
	private String displayName;

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

	public void setShareLink(String shareLink){
		this.shareLink = shareLink;
	}

	public String getShareLink(){
		return shareLink;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setNextNews(NextNews nextNews){
		this.nextNews = nextNews;
	}

	public NextNews getNextNews(){
		return nextNews;
	}

	public void setRelatedNews(List<RelatedNewsItem> relatedNews){
		this.relatedNews = relatedNews;
	}

	public List<RelatedNewsItem> getRelatedNews(){
		return relatedNews;
	}

	public void setBottomAds(List<BottomAdsItem> bottomAds){
		this.bottomAds = bottomAds;
	}

	public List<BottomAdsItem> getBottomAds(){
		return bottomAds;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPrevNews(PrevNews prevNews){
		this.prevNews = prevNews;
	}

	public PrevNews getPrevNews(){
		return prevNews;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
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

	public List<RightAdsItem> getRightAds() {
		return rightAds;
	}

	public void setRightAds(List<RightAdsItem> rightAds) {
		this.rightAds = rightAds;
	}

	public List<LeftAdsItem> getLeftAds() {
		return leftAds;
	}

	public void setLeftAds(List<LeftAdsItem> leftAds) {
		this.leftAds = leftAds;
	}

	@Override
	public String toString() {
		return "NewsDetails{" +
				"date='" + date + '\'' +
				", shareLink='" + shareLink + '\'' +
				", thumb='" + thumb + '\'' +
				", nextNews=" + nextNews +
				", relatedNews=" + relatedNews +
				", bottomAds=" + bottomAds +
				", rightAds=" + rightAds +
				", leftAds=" + leftAds +
				", description='" + description + '\'' +
				", prevNews=" + prevNews +
				", displayName='" + displayName + '\'' +
				", title='" + title + '\'' +
				", newsId='" + newsId + '\'' +
				", views='" + views + '\'' +
				'}';
	}
}