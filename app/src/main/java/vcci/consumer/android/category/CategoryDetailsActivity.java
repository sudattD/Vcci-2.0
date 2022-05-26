package vcci.consumer.android.category;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcci.consumer.android.DependencyInjectorComponent;
import vcci.consumer.android.R;
import vcci.consumer.android.api.ApiClient;
import vcci.consumer.android.api.ApiInterface;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.news_by_category.NewsByCategoryIDResponse;
import vcci.consumer.android.data.models.news_by_category.NewsItem;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CategoryDetailsActivity extends BaseActivity implements BaseView,NewsSelectionListener{

   private String news_id = "";
   private int categoryID;
   private String title = "";
   
   private RecyclerView.LayoutManager layoutManager;
   private CategoryWiseNewsAdapter categoryWiseNewsAdapter;
   private NewsSelectionListener newsSelectionListener;
   @BindView(R.id.rv_category_list)
   RecyclerView rv_news;

   @BindView(R.id.rl_leading_news)
   RelativeLayout rl_leading_news;

   @BindView(R.id.iv_main_image)
   ImageView iv_main_image;

   @BindView(R.id.tv_title)
   TextView tv_title;

   @BindView(R.id.tv_error_msg)
   TextView tv_error_msg;

   private ImageView iv_category;
   private ImageView iv_category_close;

   private CategoriesAdapter categoriesAdapter;
   private RecyclerView recyclerView;
   private RecyclerView.LayoutManager category_layoutManager;

   private LinearLayout ll_close_category;

   @Override
   protected BaseView getBaseView() {
      return this;
   }

   @Override
   protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
      injectorComponent.injectDependencies(this);
   }
   
   

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_category_details);
      ButterKnife.bind(this);
      getIntentData();
      initView();
   }

   private void getIntentData() {
      categoryID = getIntent().getIntExtra("categoryID", 0);
      title = getIntent().getStringExtra("title");

   }


   @Override
   public void initView() {
      initToolbar(title);
      rv_news.setHasFixedSize(false);
      layoutManager = new LinearLayoutManager(this);
      rv_news.setLayoutManager(layoutManager);
      newsSelectionListener = this;
      getCategorywiseDataList(String.valueOf(categoryID));
   }

   @Override
   public void showProgress() {
      showProgressBase();
   }

   @Override
   public void hideProgress() {
      hideProgressBase();
   }

   @Override
   public void showToastMessage(String toastMessage, boolean isErrortoast) {

   }

   @Override
   public void accessTokenExpired() {

   }

   private void getCategorywiseDataList(String catID) {
      showProgress();
      ApiInterface apiService =
              ApiClient.getClient().create(ApiInterface.class);
      String token = sbnriPref.getString("fcm_token");
     // Log.d(TAG, "getSliderMenu: " + token);
      Call<NewsByCategoryIDResponse> call = apiService.getNewsByCategoryID(Constants.version, Constants.DEVICE_TYPE, "get-news",
              token, "123456", "1", "100", catID);
      call.enqueue(new Callback<NewsByCategoryIDResponse>() {
         @Override
         public void onResponse(Call<NewsByCategoryIDResponse> call, Response<NewsByCategoryIDResponse> response) {
            hideProgress();
            assert response.body() != null;
            List<NewsItem> news = response.body().getNews();
            if (news.size() > 0){
               if (news.get(0).getNewsId() != null){
                  news_id = news.get(0).getNewsId();
                  if (news != null && news.size() > 0) {
                     Picasso.get().load(news.get(0).getThumb()).into(iv_main_image);
                     tv_title.setText(news.get(0).getTitle());
                  }

                  categoryWiseNewsAdapter = new CategoryWiseNewsAdapter(context, news, newsSelectionListener);
                  rv_news.setAdapter(categoryWiseNewsAdapter);
               }
            } else {
               Toast.makeText(context, "No news found for " + title, Toast.LENGTH_SHORT).show();
              doViewRelatedChanges();
            }
         }

         @Override
         public void onFailure(Call<NewsByCategoryIDResponse> call, Throwable t) {
            // Log error here since request failed
            hideProgress();
            Log.e(TAG, t.toString());
            doViewRelatedChanges();
         }
      });
   }

   private void doViewRelatedChanges() {
      tv_error_msg.setVisibility(View.VISIBLE);
      iv_main_image.setVisibility(View.GONE);
   }

   @Override
   public void onNewsSelected(String news_id) {

   }
}
