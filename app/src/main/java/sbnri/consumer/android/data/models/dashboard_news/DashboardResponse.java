package sbnri.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResponse{

	@SerializedName("bulletins")
	private List<BulletinsItem> bulletins;

	@SerializedName("right_ad")
	private List<RightAdItem> rightAd;

	@SerializedName("trending_news")
	private List<TrendingNewsItem> trendingNews;

	@SerializedName("left_ad")
	private List<LeftAdItem> leftAd;

	@SerializedName("bottom_ads")
	private List<BottomAdsItem> bottomAds;

	@SerializedName("featured_news")
	private List<FeaturedNewsItem> featuredNews;

	@SerializedName("error")
	private int error;

	@SerializedName("categories_news")
	private List<CategoriesNews> categoriesNews;

	@SerializedName("banners")
	private List<BannersItem> banners;

	@SerializedName("latest_news")
	private List<LatestNewsItem> latestNews;

	public void setBulletins(List<BulletinsItem> bulletins){
		this.bulletins = bulletins;
	}

	public List<BulletinsItem> getBulletins(){
		return bulletins;
	}

	public void setRightAd(List<RightAdItem> rightAd){
		this.rightAd = rightAd;
	}

	public List<RightAdItem> getRightAd(){
		return rightAd;
	}

	public void setTrendingNews(List<TrendingNewsItem> trendingNews){
		this.trendingNews = trendingNews;
	}

	public List<TrendingNewsItem> getTrendingNews(){
		return trendingNews;
	}

	public void setLeftAd(List<LeftAdItem> leftAd){
		this.leftAd = leftAd;
	}

	public List<LeftAdItem> getLeftAd(){
		return leftAd;
	}

	public void setBottomAds(List<BottomAdsItem> bottomAds){
		this.bottomAds = bottomAds;
	}

	public List<BottomAdsItem> getBottomAds(){
		return bottomAds;
	}

	public void setFeaturedNews(List<FeaturedNewsItem> featuredNews){
		this.featuredNews = featuredNews;
	}

	public List<FeaturedNewsItem> getFeaturedNews(){
		return featuredNews;
	}

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setCategoriesNews(List<CategoriesNews> categoriesNews){
		this.categoriesNews = categoriesNews;
	}

	public List<CategoriesNews> getCategoriesNews(){
		return categoriesNews;
	}

	public void setBanners(List<BannersItem> banners){
		this.banners = banners;
	}

	public List<BannersItem> getBanners(){
		return banners;
	}

	public void setLatestNews(List<LatestNewsItem> latestNews){
		this.latestNews = latestNews;
	}

	public List<LatestNewsItem> getLatestNews(){
		return latestNews;
	}

	@Override
 	public String toString(){
		return 
			"DashboardResponse{" + 
			"bulletins = '" + bulletins + '\'' + 
			",right_ad = '" + rightAd + '\'' + 
			",trending_news = '" + trendingNews + '\'' + 
			",left_ad = '" + leftAd + '\'' + 
			",bottom_ads = '" + bottomAds + '\'' + 
			",featured_news = '" + featuredNews + '\'' + 
			",error = '" + error + '\'' + 
			",categories_news = '" + categoriesNews + '\'' + 
			",banners = '" + banners + '\'' + 
			",latest_news = '" + latestNews + '\'' + 
			"}";
		}
}