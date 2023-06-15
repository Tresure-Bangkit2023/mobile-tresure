package id.tresure.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.tresure.android.data.remote.response.PlanResponseItem
import id.tresure.android.databinding.ItemPlaceBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat

class PlanAdapter(private val listPlace: List<PlanResponseItem>) :
    RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listPlace[position].title
            tvDescription.text = listPlace[position].city
            tvPrice.text = listPlace[position].budget?.currencyFormat() ?: "Rp. 0"
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPlace[position])
        }
    }

    override fun getItemCount(): Int {
        return listPlace.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PlanResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}