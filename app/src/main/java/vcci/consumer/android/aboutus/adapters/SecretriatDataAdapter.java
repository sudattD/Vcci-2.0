package vcci.consumer.android.aboutus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import vcci.consumer.android.R;
import vcci.consumer.android.data.models.about_us.about_us_secretariat.SecretariatItem;

public class SecretriatDataAdapter extends RecyclerView.Adapter<SecretriatDataAdapter.MyViewHolder> {

    private final List<SecretariatItem> membersList;
    private final Context context;

    public SecretriatDataAdapter(Context context, List<SecretariatItem> membersList) {
        this.context = context;
        this.membersList = membersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_personnel_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SecretariatItem dataItem = membersList.get(position);

        Picasso.get().load(dataItem.getThumb()).placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder).into(holder.iv_personnel);

        holder.tv_name.setText(dataItem.getName());
        holder.tv_designation.setText(dataItem.getDesignation());
        holder.tv_company_name.setText("");
        holder.tv_address.setText(dataItem.getDescription());
        holder.tv_mobile_no.setText(dataItem.getMobileNo());
        holder.tv_email.setText(dataItem.getEmail());
        holder.iv_location_or_info.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_info_grey));

    }

    @Override
    public int getItemCount() {
        if (membersList != null && membersList.size() > 0) {
            return membersList.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_personnel)
        CircleImageView iv_personnel;

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_designation)
        TextView tv_designation;

        @BindView(R.id.tv_company_name)
        TextView tv_company_name;

        @BindView(R.id.tv_address)
        TextView tv_address;

        @BindView(R.id.tv_mobile_no)
        TextView tv_mobile_no;

        @BindView(R.id.tv_email)
        TextView tv_email;

        @BindView(R.id.iv_location_or_info)
        ImageView iv_location_or_info;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
