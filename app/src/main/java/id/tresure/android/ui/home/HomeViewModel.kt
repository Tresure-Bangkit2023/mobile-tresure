package id.tresure.android.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.tresure.android.R
import id.tresure.android.data.local.User
import id.tresure.android.data.local.UserPreference
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.ArtResponse
import id.tresure.android.data.remote.response.ArtResponseItem
import id.tresure.android.data.remote.response.PlanRecommendationItem
import id.tresure.android.data.remote.response.PlanRecommendationResponse
import id.tresure.android.data.remote.response.PlanResponse
import id.tresure.android.data.remote.response.PlanResponseItem
import id.tresure.android.data.remote.response.ThemeParkResponse
import id.tresure.android.data.remote.response.ThemeParkResponseItem
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mListPlace = MutableLiveData<List<PlanRecommendationItem>>()
    val listPlace: LiveData<List<PlanRecommendationItem>> = mListPlace

    private val mListThemePark = MutableLiveData<List<ThemeParkResponseItem>>()
    val listThemePark: LiveData<List<ThemeParkResponseItem>> = mListThemePark

    private val mListArt = MutableLiveData<List<ArtResponseItem>>()
    val listArt: LiveData<List<ArtResponseItem>> = mListArt

    private val mListPlan = MutableLiveData<List<PlanResponseItem>>()
    val listPlan: LiveData<List<PlanResponseItem>> = mListPlan

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getAllPlans(token: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getAllPlans(token)
        client.enqueue(object : Callback<PlanResponse> {
            override fun onResponse(
                call: Call<PlanResponse>, response: Response<PlanResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListPlan.value = responseBody.data as List<PlanResponseItem>
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlanResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                mSnackBarText.value = Event(application.getString(R.string.gagal_mengambil_data))
            }
        })
    }

    fun getAllPlace(token: String, username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getAllPlanRecommendation(token, username)
        client.enqueue(object : Callback<PlanRecommendationResponse> {
            override fun onResponse(
                call: Call<PlanRecommendationResponse>,
                response: Response<PlanRecommendationResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListPlace.value = responseBody.data as List<PlanRecommendationItem>
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlanRecommendationResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                mSnackBarText.value = Event(application.getString(R.string.gagal_mengambil_data))
            }
        })
    }

    fun getThemePark(token: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getThemePark(token)
        client.enqueue(object : Callback<ThemeParkResponse> {
            override fun onResponse(
                call: Call<ThemeParkResponse>, response: Response<ThemeParkResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListThemePark.value = responseBody.data as List<ThemeParkResponseItem>
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ThemeParkResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                mSnackBarText.value = Event(application.getString(R.string.gagal_mengambil_data))
            }
        })
    }

    fun getArt(token: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getArt(token)
        client.enqueue(object : Callback<ArtResponse> {
            override fun onResponse(
                call: Call<ArtResponse>, response: Response<ArtResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListArt.value = responseBody.data as List<ArtResponseItem>
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArtResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                mSnackBarText.value = Event(application.getString(R.string.gagal_mengambil_data))
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
