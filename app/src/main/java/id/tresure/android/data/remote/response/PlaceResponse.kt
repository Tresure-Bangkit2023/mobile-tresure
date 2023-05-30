package id.tresure.android.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @field:SerializedName("response") val response: List<ResponseItem>?
)

data class ResponseItem(

    @field:SerializedName("image") val image: String? = null,

    @field:SerializedName("category_id") val categoryId: String? = null,

    @field:SerializedName("lng") val lng: Float? = null,

    @field:SerializedName("city") val city: String? = null,

    @field:SerializedName("price") val price: Float? = null,

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("rating") val rating: Float? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("id") val id: String? = null,

    @field:SerializedName("lat") val lat: Float? = null
)
