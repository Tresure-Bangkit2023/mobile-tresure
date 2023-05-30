package id.tresure.android.data.remote.api

import id.tresure.android.data.remote.response.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("places")
    fun getAllPlaces(): Call<List<PlaceResponse>>
}