package id.tresure.android.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlanResponse(

	@field:SerializedName("data")
	val data: List<PlanResponseItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
) : Parcelable

@Parcelize
data class PlanResponseItem(

	@field:SerializedName("start_location")
	val startLocation: String? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("num_of_people")
	val numOfPeople: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("budget")
	val budget: Int? = null
) : Parcelable
