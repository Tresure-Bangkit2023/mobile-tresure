package id.tresure.android.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanPlaceResponseItem(

    @field:SerializedName("depart_time") val departTime: String? = null,

    @field:SerializedName("transport_price") val transportPrice: Int? = null,

    @field:SerializedName("id") val id: String? = null,

    @field:SerializedName("place") val place: Place? = null,

    @field:SerializedName("transport_mode") val transportMode: String? = null,

    @field:SerializedName("plan_id") val planId: String? = null,

    @field:SerializedName("place_id") val placeId: Int? = null
) : Parcelable

@Parcelize
data class Place(

    @field:SerializedName("image") val image: String? = null,

    @field:SerializedName("category_id") val categoryId: Int? = null,

    @field:SerializedName("lng") val lng: Float? = null,

    @field:SerializedName("city") val city: String? = null,

    @field:SerializedName("price") val price: Int? = null,

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("rating") val rating: Float? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("id") val id: Int? = null,

    @field:SerializedName("lat") val lat: Float? = null
) : Parcelable
