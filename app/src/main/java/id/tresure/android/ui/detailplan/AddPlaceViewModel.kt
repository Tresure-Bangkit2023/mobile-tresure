package id.tresure.android.ui.detailplan

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
import id.tresure.android.data.remote.response.ArtResponseItem
import id.tresure.android.data.remote.response.DataItem
import id.tresure.android.data.remote.response.PlanByUserIdResponse
import id.tresure.android.data.remote.response.PlanPlaceResponseItem
import id.tresure.android.data.remote.response.PlanRecommendationByCityResponse
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlaceViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mListPlanRecommendationByCity = MutableLiveData<List<DataItem>?>()
    val listPlanRecommendationByCity: LiveData<List<DataItem>?> = mListPlanRecommendationByCity

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getPlanRecommendationByCity(token: String, username: String, city: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getPlanRecommendationByCity(token, username, city)
        client.enqueue(object : Callback<PlanRecommendationByCityResponse> {
            override fun onResponse(
                call: Call<PlanRecommendationByCityResponse>, response: Response<PlanRecommendationByCityResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListPlanRecommendationByCity.value = responseBody.data as List<DataItem>
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<PlanRecommendationByCityResponse>, t: Throwable) {
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