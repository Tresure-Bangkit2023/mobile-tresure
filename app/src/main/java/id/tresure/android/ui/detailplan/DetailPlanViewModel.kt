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
import id.tresure.android.data.remote.response.PlanByUserIdResponse
import id.tresure.android.data.remote.response.PlanPlaceResponseItem
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlanViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mListPlanPlace = MutableLiveData<List<PlanPlaceResponseItem>?>()
    val listPlanPlace: LiveData<List<PlanPlaceResponseItem>?> = mListPlanPlace

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getPlanPlace(token: String, userId: Int) {
        showLoading(true)
        val client = ApiConfig.getApiService().getPlanPlace(token, userId)
        client.enqueue(object : Callback<PlanByUserIdResponse> {
            override fun onResponse(
                call: Call<PlanByUserIdResponse>, response: Response<PlanByUserIdResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val planByUserIdResponseItems = responseBody.data?.plan
                        val planPlaceResponseItems =
                            planByUserIdResponseItems?.flatMap { it.planPlace ?: emptyList() }
                        mListPlanPlace.value = planPlaceResponseItems
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlanByUserIdResponse>, t: Throwable) {
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