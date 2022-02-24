package vcci.consumer.android.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vcci.consumer.android.R;
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.data.models.gallery.photos.gallery_list.GalleryItem;


public class PhotoGalleryListAdapter extends RecyclerView.Adapter<PhotoGalleryListAdapter.MyViewHolder> {

    private final List<GalleryItem> galleryItemsList;
    private final Context context;
    private final OnCommonItemClickListener itemSelectionListener;

    public PhotoGalleryListAdapter(Context context, List<GalleryItem> galleryItemsList, OnCommonItemClickListener itemSelectionListener) {
        this.context = context;
        this.galleryItemsList = galleryItemsList;
        this.itemSelectionListener = itemSelectionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GalleryItem eventItem = galleryItemsList.get(position);

        Picasso.get().load(eventItem.getThumb()).placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder).into(holder.iv_event);

        holder.tv_event_name.setText(eventItem.getTitle());
        holder.tv_event_date.setText(String.format("Event Date : %s", eventItem.getStartDate()));

        holder.card_view.setOnClickListener(v -> itemSelectionListener.onItemClick(v,eventItem));
    }

    @Override
    public int getItemCount() {
        if (galleryItemsList != null && galleryItemsList.size() > 0) {
            return galleryItemsList.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_event)
        ImageView iv_event;

        @BindView(R.id.tv_event_name)
        TextView tv_event_name;

        @BindView(R.id.tv_event_date)
        TextView tv_event_date;

        @BindView(R.id.card_view)
        CardView card_view;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
