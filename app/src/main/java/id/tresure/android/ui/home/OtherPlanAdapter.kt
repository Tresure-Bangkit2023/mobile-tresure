package id.tresure.android.ui.home

//class OtherPlanAdapter(private val listPlace: List<PlacesResponseItem>) :
//    RecyclerView.Adapter<OtherPlanAdapter.ViewHolder>() {
//
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    class ViewHolder(var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.apply {
//            tvName.text = listPlace[position].name
//            tvDescription.text = listPlace[position].description
//            tvPrice.text = listPlace[position].price.toString()
//        }
//        Glide.with(holder.itemView.rootView)
//            .load(listPlace[position].image)
//            .into(holder.binding.ivPlace)
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listPlace[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return listPlace.size
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: PlacesResponseItem)
//    }
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//}