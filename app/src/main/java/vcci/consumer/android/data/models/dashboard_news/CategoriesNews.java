package vcci.consumer.android.data.models.dashboard_news;

import com.google.gson.annotations.SerializedName;

public class CategoriesNews{

	@SerializedName("Economy")
	private Economy economy;

	@SerializedName("Jobs")
	private Jobs jobs;

	@SerializedName("Finance")
	private Finance finance;

	@SerializedName("Entrepreneurship")
	private Entrepreneurship entrepreneurship;

	@SerializedName("Markets")
	private Markets markets;

	public void setEconomy(Economy economy){
		this.economy = economy;
	}

	public Economy getEconomy(){
		return economy;
	}

	public void setJobs(Jobs jobs){
		this.jobs = jobs;
	}

	public Jobs getJobs(){
		return jobs;
	}

	public void setFinance(Finance finance){
		this.finance = finance;
	}

	public Finance getFinance(){
		return finance;
	}

	public void setEntrepreneurship(Entrepreneurship entrepreneurship){
		this.entrepreneurship = entrepreneurship;
	}

	public Entrepreneurship getEntrepreneurship(){
		return entrepreneurship;
	}

	public void setMarkets(Markets markets){
		this.markets = markets;
	}

	public Markets getMarkets(){
		return markets;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesNews{" + 
			"economy = '" + economy + '\'' + 
			",jobs = '" + jobs + '\'' + 
			",finance = '" + finance + '\'' + 
			",entrepreneurship = '" + entrepreneurship + '\'' + 
			",markets = '" + markets + '\'' + 
			"}";
		}
}