package id.tresure.android.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.PlacesResponse
import id.tresure.android.data.remote.response.PlacesResponseItem
import id.tresure.android.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mListPlace = MutableLiveData<List<PlacesResponseItem>>()
    val listPlace: LiveData<List<PlacesResponseItem>> = mListPlace

    private val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText

    private fun showLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getAllStory(token: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getAllPlaces(token)
        client.enqueue(object : Callback<List<PlacesResponse>> {
            override fun onResponse(
                call: Call<List<PlacesResponse>>, response: Response<List<PlacesResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mListPlace.value = responseBody as List<PlacesResponseItem>
                    }
                } else {
                    mSnackBarText.value = Event("Something went wrong, please try again later")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<PlacesResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                mSnackBarText.value = Event("Gagal mengambil data")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
