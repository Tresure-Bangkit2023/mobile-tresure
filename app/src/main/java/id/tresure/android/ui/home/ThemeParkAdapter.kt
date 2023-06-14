package id.tresure.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.tresure.android.data.remote.response.ThemeParkResponseItem
import id.tresure.android.databinding.ItemPlaceBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat

class ThemeParkAdapter(private val listPlace: List<ThemeParkResponseItem>) :
    RecyclerView.Adapter<ThemeParkAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listPlace[position].name
            tvDescription.text = listPlace[position].description
            tvPrice.text = listPlace[position].price?.currencyFormat() ?: "Rp. 0"
        }
        Glide.with(holder.itemView.rootView).load(listPlace[position].image).override(144, 96)
            .into(holder.binding.ivPlace)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPlace[position])
        }
    }

    override fun getItemCount(): Int {
        return listPlace.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ThemeParkResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}