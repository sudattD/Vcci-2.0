package vcci.consumer.android.membership.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vcci.consumer.android.R;
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.data.models.membership_form.MembershipForm;


public class MembershipFormAdapter extends RecyclerView.Adapter<MembershipFormAdapter.MyViewHolder> {

    private final List<MembershipForm> membershipFormsList;
    private final Context context;
    private final OnCommonItemClickListener itemSelectionListener;

    public MembershipFormAdapter(Context context, List<MembershipForm> membershipFormsList, OnCommonItemClickListener itemSelectionListener) {
        this.context = context;
        this.membershipFormsList = membershipFormsList;
        this.itemSelectionListener = itemSelectionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_membership_form_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MembershipForm dataItem = membershipFormsList.get(position);
        holder.tv_form.setText(dataItem.getForms());

        holder.iv_view.setOnClickListener(v -> itemSelectionListener.onItemClick(v, dataItem));
    }

    @Override
    public int getItemCount() {
        if (membershipFormsList != null && membershipFormsList.size() > 0) {
            return membershipFormsList.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_form)
        TextView tv_form;

        @BindView(R.id.iv_view)
        ImageView iv_view;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


  //  Message data payload: {object_id=65, object_body=, object_type=news, object_title=News: Opportunity to export in Oman}
