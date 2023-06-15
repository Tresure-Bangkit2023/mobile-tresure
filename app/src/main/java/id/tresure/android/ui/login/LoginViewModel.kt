package id.tresure.android.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.tresure.android.R
import id.tresure.android.data.local.User
import id.tresure.android.data.local.UserPreference
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.LoginResponse
import id.tresure.android.helper.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val pref: UserPreference, private val application: Application
) : ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mLoginError = MutableLiveData<Boolean>()
    val loginError: LiveData<Boolean> = mLoginError

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    fun login(username: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().login(username, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mLoginError.value = responseBody.error as Boolean
                        val token = responseBody.token as String
                        val userId = responseBody.userId as Int
                        viewModelScope.launch {
                            pref.saveUser(User(userId, username, token))
                        }
                    }
                    Log.d(TAG, "onResponse: ${responseBody?.userId.toString()}")
                } else if (response.code() == 401) {
                    mSnackBarText.value =
                        Event(application.getString(R.string.username_atau_password_salah))
                    mLoginError.value = true
                    Log.e(TAG, "onFailure: ${response.body()}")
                } else {
                    mSnackBarText.value = Event(application.getString(R.string.login_gagal))
                    Log.e(TAG, "onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                mSnackBarText.value = Event(application.getString(R.string.login_gagal))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}