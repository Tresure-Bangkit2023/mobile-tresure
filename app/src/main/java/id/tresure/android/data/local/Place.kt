package id.tresure.android.data.local

data class Place(
    val placeId: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val city: String,
    val price: Float,
    val lat: Float,
    val lng: Float,
    val rating: Float,
    val image: String
)
