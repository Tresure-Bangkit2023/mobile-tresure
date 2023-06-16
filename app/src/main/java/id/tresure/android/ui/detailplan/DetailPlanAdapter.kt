package id.tresure.android.ui.detailplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.tresure.android.data.remote.response.PlanPlaceResponseItem
import id.tresure.android.databinding.ItemPlanDetailBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat

class DetailPlanAdapter(private val listPlanPlace: List<PlanPlaceResponseItem>?) :
    RecyclerView.Adapter<DetailPlanAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemPlanDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlanDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvPrice.text = listPlanPlace?.get(position)?.place?.price?.currencyFormat() ?: "Rp. 0"
            tvName.text = listPlanPlace?.get(position)?.place?.name
            tvTime.text = listPlanPlace?.get(position)?.departTime?.substring(11, 16) ?: "00:00"
            tvTransport.text = listPlanPlace?.get(position)?.transportMode
        }
    }

    override fun getItemCount(): Int {
        return listPlanPlace?.size ?: 0
    }
}