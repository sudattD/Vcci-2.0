package vcci.consumer.android.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.api.ApiClient;
import vcci.consumer.android.api.ApiInterface;
import vcci.consumer.android.base.activity.BaseFragment;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.category.CategoriesItem;
import vcci.consumer.android.data.models.category.CategoriesResponse;
import vcci.consumer.android.home.HomeItemsAdapter;

public class CategoryFragment extends BaseFragment implements BaseView, OnCommonItemClickListener {


    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rvCategoryItems)
    RecyclerView rvCategoryItems;

    ArrayList<CategoryActionItem> categoryActionItems;
    CategoryItemsAdapter mAdapter;

    private CategoriesAdapter categoriesAdapter;

    private OnCommonItemClickListener onCommonItemClickListener;

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
            injectorComponent.injectDependencies(this);
    }

    @Override
    public View onCreateKnifeView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView();
        onCommonItemClickListener = this;
    }

    @Override
    public void initView() {
        setupCategoryItems();
    }

    private void setupCategoryItems() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //Log.d(TAG, "getSliderMenu: " + token);
        Call<CategoriesResponse> call = apiService.getCategoriesList(Constants.version, Constants.DEVICE_TYPE, "get-categories",
                "token", "123456", "1", "100");
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                assert response.body() != null;
                List<CategoriesItem> categoriesItemsWithHeader = new ArrayList<>();
                CategoriesItem categoriesItem = new CategoriesItem();
                categoriesItem.setId(-1);
                categoriesItem.setTitle("");
                categoriesItemsWithHeader.add(categoriesItem);
                List<CategoriesItem> categoriesItemsList = response.body().getCategories();
                categoriesItemsWithHeader.addAll(categoriesItemsList);
                //Log.d(TAG, "Categories received: " + categoriesItemsWithHeader.size());
                categoriesAdapter = new CategoriesAdapter(context, categoriesItemsWithHeader, onCommonItemClickListener);

                LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rvCategoryItems.setLayoutManager(manager);

                rvCategoryItems.setAdapter(categoriesAdapter);

            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                // Log error here since request failed
               // Log.e(TAG, t.toString());
            }
        });
    }

    private void setUpCategoryItems() {
        categoryActionItems = new ArrayList<>();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToastMessage(String toastMessage, boolean isErrortoast) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, Object object) {

        if (object instanceof CategoriesItem) {
            if(((CategoriesItem) object).getId() == 1) {
                //All
                showToastMessage("All clicked",false);
            }
            else if(((CategoriesItem) object).getId() == 2)
            {

            }
        }

    }
}
