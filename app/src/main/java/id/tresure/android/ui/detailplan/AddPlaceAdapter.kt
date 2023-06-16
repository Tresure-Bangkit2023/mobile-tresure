package id.tresure.android.ui.detailplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.tresure.android.data.remote.response.DataItem
import id.tresure.android.databinding.ItemPlaceBigBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat

class AddPlaceAdapter(private val listPlace: List<DataItem>) :
    RecyclerView.Adapter<AddPlaceAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemPlaceBigBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listPlace[position].name
            tvDescription.text = listPlace[position].description
            tvPrice.text = listPlace[position].price?.currencyFormat() ?: "Rp. 0"
        }
        Glide.with(holder.itemView.rootView).load(listPlace[position].image)
            .into(holder.binding.ivPlace)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPlace[position])
        }
    }

    override fun getItemCount(): Int {
        return listPlace.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}