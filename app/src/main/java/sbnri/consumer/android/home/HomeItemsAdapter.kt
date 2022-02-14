package sbnri.consumer.android.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sbnri.consumer.android.R
import sbnri.consumer.android.adapters.OnCommonItemClickListener

class HomeItemsAdapter(
        private val items:ArrayList<HomeActionItem>,val picasso: Picasso,
        val onCommonItemClickListener: OnCommonItemClickListener
) : RecyclerView.Adapter<HomeItemsAdapter.HomeItemsViewHolder>() {
    class HomeItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeActionItemName : TextView = view.findViewById(R.id.tvActionName)
        val ivHomeActionItem : ImageView = view.findViewById(R.id.ivActionImage)
        val rlActionLayout : RelativeLayout = view.findViewById(R.id.rlActionLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_action_item, parent, false)
        return HomeItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeItemsViewHolder, position: Int) {
        val item = items[position]
        holder.homeActionItemName.text = item.item
        holder.ivHomeActionItem.setImageResource(item.img)
        
        holder.rlActionLayout.setOnClickListener { v -> onCommonItemClickListener.onItemClick(v,item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}