package id.tresure.android.data.remote.api

import id.tresure.android.data.remote.response.LoginResponse
import id.tresure.android.data.remote.response.MallResponse
import id.tresure.android.data.remote.response.PlacesResponse
import id.tresure.android.data.remote.response.RegisterResponse
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
    ): Call<List<PlacesResponse>>

    @GET("places/search?category=pusat perbelanjaan")
    fun getMall(
        @Header("Authorization") token: String
    ): Call<List<MallResponse>>

    @GET("places/search?category=taman hiburan")
    fun getThemePark(
        @Header("Authorization") token: String
    ): Call<List<MallResponse>>
}