package id.tresure.android.data.remote.api

import id.tresure.android.data.remote.response.ArtResponse
import id.tresure.android.data.remote.response.CreatePlanResponse
import id.tresure.android.data.remote.response.LoginResponse
import id.tresure.android.data.remote.response.PlacesResponse
import id.tresure.android.data.remote.response.PlanByUserIdResponse
import id.tresure.android.data.remote.response.PlanResponse
import id.tresure.android.data.remote.response.RegisterResponse
import id.tresure.android.data.remote.response.ThemeParkResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("plans")
    fun getAllPlans(
        @Header("Authorization") token: String
    ): Call<PlanResponse>

    @GET("users/{user_id}/plan")
    fun getPlanByUserId(
        @Header("Authorization") token: String, @Path("user_id") userId: Int
    ): Call<PlanByUserIdResponse>

    @FormUrlEncoded
    @POST("plans")
    fun createPlan(
        @Header("Authorization") token: String,
        @Field("user_id") user_id: Int,
        @Field("title") title: String,
        @Field("num_of_people") num_of_people: Int,
        @Field("city") city: String,
        @Field("start_location") start_location: String,
        @Field("start_time") start_time: String,
        @Field("budget") budget: Float
    ): Call<CreatePlanResponse>
}