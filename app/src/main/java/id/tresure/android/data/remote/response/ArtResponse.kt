package id.tresure.android.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtResponse(

    @field:SerializedName("data") val data: List<ArtResponseItem>?,

    @field:SerializedName("error") val error: Boolean? = null
) : Parcelable

@Parcelize
data class ArtResponseItem(

    @field:SerializedName("image") val image: String? = null,

    @field:SerializedName("category_id") val categoryId: Int? = null,

    @field:SerializedName("lng") val lng: Float? = null,

    @field:SerializedName("city") val city: String? = null,

    @field:SerializedName("price") val price: Int? = null,

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("rating") val rating: Float? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("id") val id: Int? = null,

    @field:SerializedName("category") val category: Category? = null,

    @field:SerializedName("lat") val lat: Float? = null
) : Parcelable
