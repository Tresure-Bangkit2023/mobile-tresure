package id.tresure.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.tresure.android.data.local.Place
import id.tresure.android.databinding.ItemPlaceBinding

class PlaceAdapter(private val listPlace: ArrayList<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
//
//    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        var image: ImageView = binding.ivPlace
        var name: TextView = binding.tvName
        var description: TextView = binding.tvDescription
        var price: TextView = binding.tvPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = listPlace[position]
        val context = holder.itemView.context

        Glide.with(context).load(place.image).apply(RequestOptions()).into(holder.image)

        holder.name.text = place.name
        holder.description.text = place.description
        holder.price.text = place.price

//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listPlace[position])
//        }
    }

    override fun getItemCount(): Int {
        return listPlace.size
    }

//    interface OnItemClickCallback {
//        fun onItemClicked(data: Place)
//    }
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
}