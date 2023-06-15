package id.tresure.android.ui.plan

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
import id.tresure.android.data.remote.response.PlanByUserIdResponseItem
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mListPlanByUserId = MutableLiveData<List<PlanByUserIdResponseItem>>()
    val listPlanByUserId: LiveData<List<PlanByUserIdResponseItem>> = mListPlanByUserId

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getPlanByUserId(token: String, userId: Int) {
        showLoading(true)
        val client = ApiConfig.getApiService().getPlanByUserId(token, userId)
        client.enqueue(object : Callback<PlanByUserIdResponse> {
            override fun onResponse(
                call: Call<PlanByUserIdResponse>, response: Response<PlanByUserIdResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListPlanByUserId.value =
                            responseBody.data?.plan as List<PlanByUserIdResponseItem>
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