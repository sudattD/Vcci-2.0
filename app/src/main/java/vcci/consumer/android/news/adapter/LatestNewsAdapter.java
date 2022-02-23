package vcci.consumer.android.news.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vcci.consumer.android.R;
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.data.models.dashboard_news.LatestNewsItem;


public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.MyViewHolder> {

    private final List<LatestNewsItem> newsItems;
    private final Context context;
    private final OnCommonItemClickListener newsSelectionListener;

    public LatestNewsAdapter(Context context, List<LatestNewsItem> newsItems, OnCommonItemClickListener newsSelectionListener) {
        this.context = context;
        this.newsItems = newsItems;
        this.newsSelectionListener = newsSelectionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_news_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LatestNewsItem item = newsItems.get(position);

        Picasso.get().load(item.getThumb()).placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder).into(holder.iv_news_thumb);

        holder.tv_headline.setText(item.getTitle());
        Typeface custom_font = Typeface.createFromAsset(Objects.requireNonNull(context).getAssets(), "fonts/OpenSans-Regular.ttf");
        holder.tv_headline.setTypeface(custom_font);

        holder.rl_news_item.setOnClickListener(v -> newsSelectionListener.onItemClick(v,item));


    }

    @Override
    public int getItemCount() {
        if (newsItems != null && newsItems.size() > 0) {
            return newsItems.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_news_thumb)
        ImageView iv_news_thumb;

        @BindView(R.id.tv_headline)
        TextView tv_headline;

        @BindView(R.id.v_divider)
        View v_divider;

        @BindView(R.id.rl_news_item)
        RelativeLayout rl_news_item;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
