package vcci.consumer.android.aboutus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vcci.consumer.android.R;
import vcci.consumer.android.data.models.about_us.about_us_personnel.DataItem;


public class PastPresidentAdapter extends RecyclerView.Adapter<PastPresidentAdapter.MyViewHolder> {

    private final List<DataItem> personnelDataList;
    private final Context context;

    public PastPresidentAdapter(Context context, List<DataItem> personnelDataList) {
        this.context = context;
        this.personnelDataList = personnelDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_president_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataItem dataItem = personnelDataList.get(position);
        holder.tv_name.setText(dataItem.getName());
        holder.tv_year.setText(dataItem.getYear());

    }

    @Override
    public int getItemCount() {
        if (personnelDataList != null && personnelDataList.size() > 0) {
            return personnelDataList.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_year)
        TextView tv_year;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
