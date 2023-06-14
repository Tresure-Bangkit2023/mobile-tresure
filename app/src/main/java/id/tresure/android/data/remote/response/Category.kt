package id.tresure.android.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("id") val id: Int? = null
) : Parcelable
