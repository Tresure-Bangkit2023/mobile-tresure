package id.tresure.android.data.local

data class Plan(
    val planId: String,
    val userId: Int,
    val title: String,
    val numOfPeople: Int,
    val city: String,
    val startLocation: String,
    val startTime: String
)
