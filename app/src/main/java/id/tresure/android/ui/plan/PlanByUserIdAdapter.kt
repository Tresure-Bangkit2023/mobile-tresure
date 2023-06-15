package id.tresure.android.ui.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.tresure.android.data.remote.response.PlanByUserIdResponseItem
import id.tresure.android.databinding.ItemPlanBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat

class PlanByUserIdAdapter(private val listPlanByUserId: List<PlanByUserIdResponseItem>) :
    RecyclerView.Adapter<PlanByUserIdAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listPlanByUserId[position].title
            tvDescription.text = listPlanByUserId[position].city
            tvPrice.text = listPlanByUserId[position].budget?.currencyFormat() ?: "Rp. 0"
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPlanByUserId[position])
        }
    }

    override fun getItemCount(): Int {
        return listPlanByUserId.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PlanByUserIdResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}