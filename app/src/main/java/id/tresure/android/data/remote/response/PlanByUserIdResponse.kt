package id.tresure.android.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanByUserIdResponse(

    @field:SerializedName("data") val data: Data? = null,

    @field:SerializedName("error") val error: Boolean? = null
) : Parcelable

@Parcelize
data class Data(

    @field:SerializedName("password") val password: String? = null,

    @field:SerializedName("full_name") val fullName: String? = null,

    @field:SerializedName("id") val id: Int? = null,

    @field:SerializedName("plan") val plan: List<PlanByUserIdResponseItem>?,

    @field:SerializedName("email") val email: String? = null,

    @field:SerializedName("username") val username: String? = null
) : Parcelable

@Parcelize
data class PlanByUserIdResponseItem(

    @field:SerializedName("start_location") val startLocation: String? = null,

    @field:SerializedName("start_time") val startTime: String? = null,

//    @field:SerializedName("PlanPlace") val planPlace: List<>? = null,

    @field:SerializedName("user_id") val userId: Int? = null,

    @field:SerializedName("city") val city: String? = null,

    @field:SerializedName("num_of_people") val numOfPeople: Int? = null,

    @field:SerializedName("id") val id: String? = null,

    @field:SerializedName("title") val title: String? = null,

    @field:SerializedName("budget") val budget: Int? = null
) : Parcelable
