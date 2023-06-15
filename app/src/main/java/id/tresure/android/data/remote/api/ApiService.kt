package id.tresure.android.data.remote.api

import id.tresure.android.data.remote.response.ArtResponse
import id.tresure.android.data.remote.response.LoginResponse
import id.tresure.android.data.remote.response.PlacesResponse
import id.tresure.android.data.remote.response.PlanResponse
import id.tresure.android.data.remote.response.RegisterResponse
import id.tresure.android.data.remote.response.ThemeParkResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("full_name") full_name: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("username") username: String, @Field("password") password: String
    ): Call<LoginResponse>

    @GET("places")
    fun getAllPlaces(
        @Header("Authorization") token: String
    ): Call<PlacesResponse>

    @GET("places/search?category=budaya")
    fun getArt(
        @Header("Authorization") token: String
    ): Call<ArtResponse>

    @GET("places/search?category=taman hiburan")
    fun getThemePark(
        @Header("Authorization") token: String
    ): Call<ThemeParkResponse>

    @POST("plans")
    fun createPlan(
        @Field("title") title: String,
        @Field("num_of_people") num_of_people: Int,
        @Field("city") city: String,
        @Field("start_location") start_location: String,
        @Field("start_time") start_time: Int,
    ): Call<PlanResponse>
}