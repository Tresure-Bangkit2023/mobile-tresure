package id.tresure.android.ui.createplan

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.tresure.android.R
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.PlanResponse
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePlanViewModel(private val application: Application) : ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    private val mIsPlanCreated = MutableLiveData<Boolean>()
    val isPlanCreated: LiveData<Boolean> = mIsPlanCreated

    init {
        mIsPlanCreated.value = false
    }

    fun createPlan(
        title: String,
        person: Int,
        city: String,
        startDestination: String,
        startTime: Int
    ) {
        showLoading(true)
        val client =
            ApiConfig.getApiService().createPlan(title, person, city, startDestination, startTime)
        client.enqueue(object : Callback<PlanResponse> {
            override fun onResponse(
                call: Call<PlanResponse>, response: Response<PlanResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mIsPlanCreated.value = true
                    }
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.rencana_gagal_dibuat))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlanResponse>, t: Throwable) {
                showLoading(false)
                mSnackBarText.value =
                    Event(application.getString(R.string.ada_yang_salah_coba_lagi_nanti))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}