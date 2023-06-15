package id.tresure.android.ui.createplan

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
import id.tresure.android.data.remote.response.CreatePlanResponse
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePlanViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    private val mIsPlanCreated = MutableLiveData<Boolean>()
    val isPlanCreated: LiveData<Boolean> = mIsPlanCreated

    init {
        mIsPlanCreated.value = false
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun createPlan(
        token: String,
        user_id: Int,
        title: String,
        num_of_people: Int,
        city: String,
        startDestination: String,
        startTime: String,
        budget: Float
    ) {
        showLoading(true)
        val client = ApiConfig.getApiService().createPlan(
            token,
            user_id,
            title,
            num_of_people,
            city,
            startDestination,
            startTime,
            budget
        )
        client.enqueue(object : Callback<CreatePlanResponse> {
            override fun onResponse(
                call: Call<CreatePlanResponse>, response: Response<CreatePlanResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mIsPlanCreated.value = true
                    }
                    Log.d(TAG, "onResponse: ${responseBody.toString()}")
                } else {
                    mSnackBarText.value =
                        Event(application.getString(R.string.rencana_gagal_dibuat))
                    Log.e(TAG, "onFailure: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CreatePlanResponse>, t: Throwable) {
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
        private const val TAG = "CreatePlanViewModel"
    }
}