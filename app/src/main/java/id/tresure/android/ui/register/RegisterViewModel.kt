package id.tresure.android.ui.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.RegisterResponse
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val application: Application) : ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    private val mIsUserCreated = MutableLiveData<Boolean>()
    val isUserCreated: LiveData<Boolean> = mIsUserCreated

    init {
        mIsUserCreated.value = false
    }

    fun register(username: String, password: String, email: String, full_name: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().register(username, password, email, full_name)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val isError = responseBody.error as Boolean
                        if (isError) {
                            mSnackBarText.value = Event("Email is already taken")
                        }
                        mIsUserCreated.value = true
                    }
                } else {
                    mSnackBarText.value = Event("Email is already taken")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                mSnackBarText.value = Event("Something went wrong")
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